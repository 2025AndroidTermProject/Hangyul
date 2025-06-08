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
import com.android.hangyul.ui.components.NaviBar
import com.android.hangyul.ui.components.TopBar
import com.android.hangyul.ui.theme.HangyulTheme
import kotlinx.coroutines.Dispatchers
import com.android.hangyul.util.DiaryUtil

@Composable
fun DiaryPage(
    navController: NavController,
    entries: List<DiaryEntry> = listOf(),
    fileName: String? = null
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

    fun startRecording() {
        try {
            val audioFile = File(context.filesDir, "recording_${System.currentTimeMillis()}.amr")
            recordFilePath.value = audioFile.absolutePath

            recorder.value = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.AMR_NB)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                setOutputFile(audioFile.absolutePath)
                prepare()
                start()
            }

            recordingState.value = RecordingState.Recording
            seconds.value = 0
            timerJob = coroutineScope.launch {
                while (true) {
                    delay(1000)
                    seconds.value++
                    val min = seconds.value / 60
                    val sec = seconds.value % 60
                    duration.value = String.format("%02d:%02d", min, sec)
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
            timerJob = null
            recordingState.value = RecordingState.Finished

            // 녹음 완료 후 텍스트 변환 시작
            recordFilePath.value?.let { filePath ->
                coroutineScope.launch(Dispatchers.IO) {
                    val apiKey = BuildConfig.SPEECH_TO_TEXT_API_KEY
                    val result = DiaryUtil.speechToText(File(filePath), apiKey)
                    withContext(Dispatchers.Main) {
                        convertedText.value = result
                        if (result == null) {
                            Toast.makeText(context, "음성 변환에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        } else if (result.isNullOrEmpty()) {
                             Toast.makeText(context, "변환된 텍스트가 없습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "녹음 중지에 실패했습니다.\n${e.message}", Toast.LENGTH_SHORT).show()
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
                fileName = recordFilePath.value,
                onClick = {
                    if (!recordFilePath.value.isNullOrBlank() && !convertedText.value.isNullOrBlank()) {
                        navController.navigate("diaryDetail/$dateForRoute?convertedText=${convertedText.value}")
                    } else if (recordFilePath.value.isNullOrBlank()) {
                        Toast.makeText(context, "녹음된 파일이 없습니다.", Toast.LENGTH_SHORT).show()
                    } else if (convertedText.value.isNullOrBlank()) {
                        Toast.makeText(context, "텍스트 변환 중이거나 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            DiaryHistoryCard(
                entries = entries,
                onHeaderClick = { navController.navigate("diaryHistory") },
                onEntryClick = { entry ->
                    navController.navigate("diaryDetail/${entry.date}")
                }
            )

            Spacer(modifier = Modifier.height(80.dp)) // Bottom NavBar 공간 고려
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DiaryPagePreview() {
    val dummyEntries = listOf(
        DiaryEntry("5월 26일", "😊", "행복", "오늘은 기분이 좋았어요!","위로"),
        DiaryEntry("5월 25일", "🥺", "슬픔", "오늘은 혼자있는 시간이 많았나봐요","위로")
    )

    HangyulTheme {
        DiaryPage(
            navController = androidx.navigation.compose.rememberNavController(),
            entries = dummyEntries,
            fileName = "25_06_04.mp3"
        )
    }
}
