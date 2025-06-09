package com.android.hangyul.ml

import android.content.Context
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class EmotionAnalyzer(private val context: Context) {
    private var interpreter: Interpreter? = null
    private val modelPath = "emotion_model.tflite"
    private val inputSize = 128 // 모델의 입력 크기에 맞게 조정
    private val outputSize = 7 // 감정 클래스 수에 맞게 조정

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
        // 텍스트를 모델 입력 형식으로 변환
        val inputBuffer = preprocessText(text)
        
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
        val emotions = arrayOf("기쁨", "슬픔", "분노", "불안", "중립", "놀람", "혐오")
        val maxIndex = results.indices.maxByOrNull { results[it] } ?: 0
        return emotions[maxIndex]
    }

    private fun preprocessText(text: String): ByteBuffer {
        // 텍스트 전처리 로직 구현
        // 실제 구현에서는 텍스트를 토큰화하고 임베딩하는 과정이 필요
        val inputBuffer = ByteBuffer.allocateDirect(inputSize * 4)
        inputBuffer.order(ByteOrder.nativeOrder())
        
        // 예시로, 텍스트 길이에 따라 더미 데이터에 영향을 주도록 함 (실제 로직 아님)
        val textLengthNormalized = text.length.toFloat() / 100.0f // 예시: 길이를 0-100으로 가정
        for (i in 0 until inputSize) {
            inputBuffer.putFloat(textLengthNormalized)
        }
        
        return inputBuffer
    }

    fun close() {
        interpreter?.close()
    }
} 