package com.android.hangyul.ml

import android.content.Context
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.util.*

class EmotionAnalyzer(private val context: Context) {
    private var interpreter: Interpreter? = null
    private val modelPath = "emotion_model.tflite"
    private val maxSequenceLength = 128
    private val outputSize = 7 // 감정 클래스 수

    private val emotions = arrayOf("기쁨", "슬픔", "분노", "불안", "중립", "놀람", "혐오")
    
    // KcELECTRA 토크나이저의 기본 어휘
    private val vocab = mapOf(
        "[PAD]" to 0,
        "[UNK]" to 1,
        "[CLS]" to 2,
        "[SEP]" to 3,
        "[MASK]" to 4,
        // 기본적인 한글 자모와 자주 사용되는 단어들
        "가" to 5, "나" to 6, "다" to 7, "라" to 8, "마" to 9,
        "바" to 10, "사" to 11, "아" to 12, "자" to 13, "차" to 14,
        "카" to 15, "타" to 16, "파" to 17, "하" to 18,
        "행복" to 19, "슬픔" to 20, "화남" to 21, "걱정" to 22,
        "평온" to 23, "놀람" to 24, "싫음" to 25
    )

    init {
        loadModel()
    }

    private fun loadModel() {
        try {
            val assetManager = context.assets
            val modelFile = assetManager.openFd(modelPath)
            val inputStream = FileInputStream(modelFile.fileDescriptor)
            val fileChannel = inputStream.channel
            val startOffset = modelFile.startOffset
            val declaredLength = modelFile.declaredLength
            val mappedByteBuffer = fileChannel.map(
                FileChannel.MapMode.READ_ONLY,
                startOffset,
                declaredLength
            )
            interpreter = Interpreter(mappedByteBuffer)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun analyzeEmotion(text: String): String {
        try {
            // 텍스트 전처리 및 토큰화
            val tokens = tokenize(text)
            
            // 입력 버퍼 준비
            val inputBuffer = ByteBuffer.allocateDirect(maxSequenceLength * 4)
            inputBuffer.order(ByteOrder.nativeOrder())
            
            // 토큰을 입력 버퍼에 채우기
            for (i in tokens.indices) {
                inputBuffer.putFloat(tokens[i].toFloat())
            }
            // 나머지 부분을 패딩으로 채우기
            for (i in tokens.size until maxSequenceLength) {
                inputBuffer.putFloat(0f)
            }
            
            // 출력 버퍼 준비
            val outputBuffer = ByteBuffer.allocateDirect(outputSize * 4)
            outputBuffer.order(ByteOrder.nativeOrder())
            
            // 모델 실행
            interpreter?.run(inputBuffer, outputBuffer)
            
            // 결과 해석
            val results = FloatArray(outputSize)
            outputBuffer.rewind()
            outputBuffer.asFloatBuffer().get(results)
            
            // 가장 높은 확률의 감정 반환
            val maxIndex = results.indices.maxByOrNull { results[it] } ?: 0
            return emotions[maxIndex]
        } catch (e: Exception) {
            e.printStackTrace()
            return "중립" // 오류 발생 시 기본값 반환
        }
    }

    private fun tokenize(text: String): List<Int> {
        val tokens = mutableListOf<Int>()
        
        // [CLS] 토큰 추가
        tokens.add(vocab["[CLS]"] ?: 1)
        
        // 텍스트를 문자 단위로 분리하고 토큰화
        text.forEach { char ->
            val token = vocab[char.toString()] ?: vocab["[UNK]"] ?: 1
            tokens.add(token)
        }
        
        // [SEP] 토큰 추가
        tokens.add(vocab["[SEP]"] ?: 3)
        
        // 최대 길이에 맞게 패딩
        while (tokens.size < maxSequenceLength) {
            tokens.add(vocab["[PAD]"] ?: 0)
        }
        
        return tokens.take(maxSequenceLength)
    }

    fun close() {
        interpreter?.close()
    }
} 