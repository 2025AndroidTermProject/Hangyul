package com.android.hangyul.ml

import android.content.Context
import android.util.Log
import org.json.JSONObject
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class EmotionAnalyzer(private val context: Context) {
    private var interpreter: Interpreter? = null
    private val modelPath = "sentiment_model_improved.tflite"
    private val tokenizerJsonPath = "tokenizer.json"
    private val maxSequenceLength = 128
    private val outputSize = 60

    private val emotions = arrayOf(
        "분노", "툴툴대는", "좌절한", "짜증내는", "방어적인", "악의적인", "안달하는", "구역질 나는", "노여워하는", "성가신",
        "슬픔", "실망한", "비통한", "후회되는", "우울한", "마비된", "염세적인", "눈물이 나는", "낙담한", "환멸을 느끼는",
        "불안", "두려운", "스트레스 받는", "취약한", "혼란스러운", "당혹스러운", "회의적인", "걱정스러운", "조심스러운", "초조한",
        "상처", "질투하는", "배신당한", "고립된", "충격 받은", "가난한 불우한", "희생된", "억울한", "괴로워하는", "버려진",
        "당황", "고립된(당황한)", "남의 시선을 의식하는", "외로운", "열등감", "죄책감의", "부끄러운", "혐오스러운", "한심한", "혼란스러운(당황한)",
        "기쁨", "감사하는", "신뢰하는", "편안한", "만족스러운", "흥분", "느긋", "안도", "신이 난", "자신하는"
    )

    private var vocabulary: Map<String, Int> = emptyMap()
    private val unkToken = "[UNK]"
    private val clsToken = "[CLS]"
    private val sepToken = "[SEP]"
    private val padToken = "[PAD]"
    private val subwordPrefix = "##"

    init {
        try {
            loadModel()
            loadVocabularyFromJson()
        } catch (e: Exception) {
            Log.e("EmotionAnalyzer", "초기화 실패: ${e.message}", e)
        }
    }

    private fun loadModel() {
        val assetManager = context.assets
        val fileDescriptor = assetManager.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val mappedByteBuffer: MappedByteBuffer = fileChannel.map(
            FileChannel.MapMode.READ_ONLY,
            fileDescriptor.startOffset,
            fileDescriptor.declaredLength
        )
        interpreter = Interpreter(mappedByteBuffer)
        Log.d("EmotionAnalyzer", "모델 로드 완료: $modelPath")
    }

    private fun loadVocabularyFromJson() {
        val jsonStr = context.assets.open(tokenizerJsonPath).bufferedReader().use { it.readText() }
        val root = JSONObject(jsonStr)
        val vocabJson = root.getJSONObject("model").getJSONObject("vocab")
        val keys = vocabJson.keys()
        val vocabMap = mutableMapOf<String, Int>()
        while (keys.hasNext()) {
            val token = keys.next()
            vocabMap[token] = vocabJson.getInt(token)
        }
        vocabulary = vocabMap
        Log.d("EmotionAnalyzer", "어휘 로드 완료: ${vocabulary.size}개 토큰")
    }

    private fun tokenize(text: String): List<Int> {
        val tokens = mutableListOf<Int>()
        tokens.add(vocabulary[clsToken] ?: 2)

        val words = text.trim().split(" ")
        for (word in words) {
            var start = 0
            while (start < word.length) {
                var end = word.length
                var matched: String? = null

                while (start < end) {
                    val sub = if (start == 0) {
                        word.substring(start, end)
                    } else {
                        "$subwordPrefix${word.substring(start, end)}"
                    }
                    if (vocabulary.containsKey(sub)) {
                        matched = sub
                        break
                    }
                    end--
                }

                if (matched != null) {
                    tokens.add(vocabulary[matched]!!)
                    start += if (matched.startsWith(subwordPrefix)) matched.length - 2 else matched.length
                } else {
                    tokens.add(vocabulary[unkToken] ?: 1)
                    start++
                }
            }
        }

        tokens.add(vocabulary[sepToken] ?: 3)
        while (tokens.size < maxSequenceLength) {
            tokens.add(vocabulary[padToken] ?: 0)
        }
        return tokens.take(maxSequenceLength)
    }

    fun analyzeEmotion(text: String): String {
        if (interpreter == null || vocabulary.isEmpty()) {
            Log.e("EmotionAnalyzer", "모델 또는 어휘가 로드되지 않음")
            return "중립"
        }

        val tokens = tokenize(text)
        val inputIds = IntArray(maxSequenceLength) { tokens[it] }
        val attentionMask = IntArray(maxSequenceLength) { if (tokens[it] != (vocabulary[padToken] ?: 0)) 1 else 0 }
        val tokenTypeIds = IntArray(maxSequenceLength) { 0 }

        val inputBuffer = ByteBuffer.allocateDirect(maxSequenceLength * 4).order(ByteOrder.nativeOrder()).asIntBuffer().put(inputIds).rewind()
        val attentionBuffer = ByteBuffer.allocateDirect(maxSequenceLength * 4).order(ByteOrder.nativeOrder()).asIntBuffer().put(attentionMask).rewind()
        val tokenTypeBuffer = ByteBuffer.allocateDirect(maxSequenceLength * 4).order(ByteOrder.nativeOrder()).asIntBuffer().put(tokenTypeIds).rewind()

        val inputs = arrayOf<Any>(inputBuffer, attentionBuffer, tokenTypeBuffer)
        val outputBuffer = ByteBuffer.allocateDirect(outputSize * 4).order(ByteOrder.nativeOrder())
        val outputs = mutableMapOf<Int, Any>(0 to outputBuffer)

        interpreter?.runForMultipleInputsOutputs(inputs, outputs)

        val results = FloatArray(outputSize)
        outputBuffer.rewind()
        outputBuffer.asFloatBuffer().get(results)

        val exp = results.map { Math.exp(it.toDouble()).toFloat() }
        val softmax = exp.map { it / exp.sum() }

        val maxIndex = softmax.indices.maxByOrNull { softmax[it] } ?: 0
        return emotions[maxIndex]
    }

    fun close() {
        interpreter?.close()
    }
}
