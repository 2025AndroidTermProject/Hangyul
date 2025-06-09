package com.android.hangyul.ui.screen.diary

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.os.Build
import android.util.Base64
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.hangyul.BuildConfig
import com.google.gson.Gson
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import java.util.Date
import java.time.ZoneId
import com.android.hangyul.ui.components.NaviBar
import com.android.hangyul.ui.components.TopBar
import com.android.hangyul.ui.theme.HangyulTheme
import kotlinx.coroutines.Dispatchers
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.hangyul.viewmodel.DiaryViewModel
import com.android.hangyul.data.DiaryEntry as DataDiaryEntry

@Composable
fun DiaryPage(
    navController: NavController,
    viewModel: DiaryViewModel = viewModel()
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val today = remember { LocalDate.now() }
    val formattedDate = today.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일 (E)", Locale.KOREAN))
    val dateForRoute = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    val recordingState = remember { mutableStateOf(RecordingState.Idle) }
    val duration = remember { mutableStateOf("00:00") }
    val recorder = remember { mutableStateOf<MediaRecorder?>(null) }
    val recordFilePath = remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()
    var timerJob by remember { mutableStateOf<Job?>(null) }
    val seconds = remember { mutableStateOf(0) }
    val recordAudioPermission = Manifest.permission.RECORD_AUDIO
    val convertedText = remember { mutableStateOf<String?>(null) }
    val entries by viewModel.allEntries.collectAsState(initial = emptyList())

    val hasTodayEntry = remember(entries) {
        entries.any { entry ->
            val entryDate = entry.date?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()
            entryDate == today
        }
    }

    fun startRecording() {
        try {
            val audioFile = File(context.filesDir, "recording_${System.currentTimeMillis()}.amr")
            recordFilePath.value = audioFile.absolutePath

            recorder.value = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(context)
            } else {
                MediaRecorder()
            }.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.AMR_NB)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                setOutputFile(recordFilePath.value)
                prepare()
                start()
            }

            recordingState.value = RecordingState.Recording
            seconds.value = 0
            timerJob = coroutineScope.launch {
                while (true) {
                    delay(1000)
                    seconds.value++
                    val minutes = String.format("%02d", seconds.value / 60)
                    val secs = String.format("%02d", seconds.value % 60)
                    duration.value = "$minutes:$secs"
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "녹음 시작에 실패했습니다.\n${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun stopRecording() {
        try {
            recorder.value?.apply {
                stop()
                release()
            }
            recorder.value = null
            timerJob?.cancel()
            recordingState.value = RecordingState.Finished

            // 녹음 파일을 텍스트로 변환
            recordFilePath.value?.let { filePath ->
                coroutineScope.launch {
                    try {
                        val text = convertAudioToText(filePath)
                        convertedText.value = text
                        // 감정 분석 및 DB 저장
                        if (!text.isNullOrBlank()) {
                            viewModel.addDiaryEntry(text)
                        } else {
                            Toast.makeText(context, "변환된 텍스트가 없습니다.", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(context, "텍스트 변환에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            startRecording()
            recordingState.value = RecordingState.Recording
        } else {
            Toast.makeText(context, "마이크 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(Color(0xFFF1F0FF))
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            TopComment("오늘의 하루를 목소리로 기록해보세요")

            Spacer(modifier = Modifier.height(12.dp))

            RecordingStatusCard(
                date = formattedDate,
                state = recordingState.value,
                durationText = duration.value,
                onClick = {
                    when (recordingState.value) {
                        RecordingState.Idle -> {
                            if (ContextCompat.checkSelfPermission(context, recordAudioPermission) == PackageManager.PERMISSION_GRANTED) {
                                startRecording()
                                recordingState.value = RecordingState.Recording
                            } else {
                                permissionLauncher.launch(recordAudioPermission)
                            }
                        }
                        RecordingState.Recording -> {
                            stopRecording()
                        }
                        RecordingState.Finished -> {
                            // 녹음 완료 후 초기화 시 기존 파일 경로 및 변환 텍스트도 초기화
                            duration.value = "00:00"
                            seconds.value = 0
                            recordFilePath.value = null // 파일 경로 초기화
                            convertedText.value = null // 변환 텍스트 초기화
                            recordingState.value = RecordingState.Idle
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            ConvertedDiaryCard(
                hasTodayEntry = hasTodayEntry,
                formattedDate = formattedDate,
                onClick = {
                    if (hasTodayEntry) {
                        val todayEntry = entries.firstOrNull { entry ->
                            val entryDate = entry.date?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()
                            entryDate == today
                        }
                        todayEntry?.let { entry ->
                            navController.navigate("diaryDetail/${entry.id}")
                        } ?: Toast.makeText(context, "오늘 일기를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "아직 작성된 오늘의 일기가 없어요.", Toast.LENGTH_SHORT).show()
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            DiaryHistoryCard(
                entries = entries,
                onHeaderClick = { navController.navigate("diaryHistory") },
                onEntryClick = { entry ->
                    navController.navigate("diaryDetail/${entry.id}")
                }
            )

            Spacer(modifier = Modifier.height(80.dp)) // Bottom NavBar 공간 고려
        }
    }
}

private suspend fun convertAudioToText(filePath: String): String? {
    return withContext(Dispatchers.IO) {
        val audioFile = File(filePath)
        if (!audioFile.exists()) {
            return@withContext null
        }

        val client = OkHttpClient()
        val audioBytes = audioFile.readBytes()
        val base64Audio = Base64.encodeToString(audioBytes, Base64.NO_WRAP)

        val json = Gson().toJson(mapOf(
            "config" to mapOf(
                "encoding" to "AMR",
                "sampleRateHertz" to 8000,
                "languageCode" to "ko-KR"
            ),
            "audio" to mapOf(
                "content" to base64Audio
            )
        ))

        val requestBody = json.toRequestBody("application/json; charset=utf-8".toMediaType())
        val request = Request.Builder()
            .url("https://speech.googleapis.com/v1/speech:recognize?key=${BuildConfig.SPEECH_TO_TEXT_API_KEY}")
            .post(requestBody)
            .build()

        try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val responseBody = response.body?.string()
                val responseJson = Gson().fromJson(responseBody, Map::class.java) as Map<*, *>
                val results = responseJson["results"] as? List<*> ?: emptyList<Any>()
                if (results.isNotEmpty()) {
                    val firstResult = results[0] as Map<*, *>
                    val alternatives = firstResult["alternatives"] as? List<*> ?: emptyList<Any>()
                    if (alternatives.isNotEmpty()) {
                        val firstAlternative = alternatives[0] as Map<*, *>
                        return@withContext firstAlternative["transcript"] as? String
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        null
    }
}

private fun getEmojiForEmotion(emotion: String): String {
    return when (emotion) {
        "기쁨" -> "😊"
        "슬픔" -> "😢"
        "분노" -> "😠"
        "불안" -> "😰"
        "중립" -> "😐"
        "놀람" -> "😲"
        "혐오" -> "🤢"
        else -> "😐"
    }
}

@Preview(showBackground = true)
@Composable
fun DiaryPagePreview() {
    HangyulTheme {
        DiaryPage(
            navController = androidx.navigation.compose.rememberNavController()
        )
    }
}
