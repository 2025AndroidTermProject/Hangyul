package com.android.hangyul.util

import android.util.Base64
import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

object DiaryUtil {
    private const val TAG = "DiaryUtil"

    suspend fun speechToText(audioFile: File, apiKey: String): String? = withContext(Dispatchers.IO) {
        try {
            Log.d(TAG, "음성 변환 시작: ${audioFile.absolutePath}")
            
            if (!audioFile.exists()) {
                Log.e(TAG, "오디오 파일이 존재하지 않음: ${audioFile.absolutePath}")
                return@withContext null
            }

            val audioBytes = audioFile.readBytes()
            Log.d(TAG, "오디오 파일 크기: ${audioBytes.size} bytes")
            
            val audioBase64 = Base64.encodeToString(audioBytes, Base64.NO_WRAP)
            Log.d(TAG, "Base64 인코딩 완료")

            val json = """
                {
                  "config": {
                    "encoding": "AMR",
                    "sampleRateHertz": 8000,
                    "languageCode": "ko-KR",
                    "enableWordTimeOffsets": false
                  },
                  "audio": {
                    "content": "$audioBase64"
                  }
                }
            """.trimIndent()

            Log.d(TAG, "API 요청 준비 완료")
            val requestBody = json.toRequestBody("application/json; charset=utf-8".toMediaType())
            val request = Request.Builder()
                .url("https://speech.googleapis.com/v1/speech:recognize?key=$apiKey")
                .post(requestBody)
                .build()

            val client = OkHttpClient()
            Log.d(TAG, "API 요청 시작")
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()
            
            Log.d(TAG, "API 응답 코드: ${response.code}")
            Log.d(TAG, "API 응답 내용: $responseBody")

            if (response.isSuccessful && responseBody != null) {
                val gson = Gson()
                val responseJson = gson.fromJson(responseBody, Map::class.java)
                val results = responseJson["results"] as? List<Map<String, Any>>
                val transcript = results?.firstOrNull()?.get("alternatives") as? List<Map<String, Any>>
                val finalTranscript = transcript?.firstOrNull()?.get("transcript") as? String
                
                if (finalTranscript != null) {
                    Log.d(TAG, "음성 변환 성공: $finalTranscript")
                } else {
                    Log.e(TAG, "변환된 텍스트가 없음")
                }
                
                finalTranscript
            } else {
                Log.e(TAG, "API 오류: ${response.code} - $responseBody")
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "음성 변환 중 오류 발생", e)
            null
        }
    }
} 