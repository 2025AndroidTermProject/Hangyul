package com.android.hangyul.ui.screen.diary

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.hangyul.ui.components.NaviBar
import com.android.hangyul.ui.components.TopBar
import com.android.hangyul.ui.theme.HangyulTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun DiaryPage(
    navController: NavController,
    entries: List<DiaryEntry> = listOf(),
    fileName: String? = null
) {
    val today = remember { LocalDate.now() }
    val formattedDate = today.format(DateTimeFormatter.ofPattern("yyyy년 M월 d일 (E)", Locale.KOREAN))
    val dateForRoute = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    val recordingState = remember { mutableStateOf(RecordingState.Idle) }
    val duration = remember { mutableStateOf("00:00") }

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
                    recordingState.value = when (recordingState.value) {
                        RecordingState.Idle -> RecordingState.Recording
                        RecordingState.Recording -> RecordingState.Finished
                        RecordingState.Finished -> RecordingState.Idle
                    }
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            ConvertedDiaryCard(
                fileName = fileName,
                onClick = {
                    if (!fileName.isNullOrBlank()) {
                        navController.navigate("diaryDetail/$dateForRoute")
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
