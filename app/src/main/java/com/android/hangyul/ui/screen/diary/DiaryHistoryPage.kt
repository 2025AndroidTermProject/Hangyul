package com.android.hangyul.ui.screen.diary

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.hangyul.R
import com.android.hangyul.ui.components.TopBar
import com.android.hangyul.ui.theme.HangyulTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.hangyul.viewmodel.DiaryViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.android.hangyul.data.DiaryEntry as DataDiaryEntry
import android.app.Application
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun DiaryHistoryPage(
    navController: NavController,
    viewModel: DiaryViewModel = viewModel()
) {
    val entries by viewModel.allEntries.collectAsState(initial = emptyList())

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(Color(0xFFF1F0FF))
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {

            TopComment("지난 일기 기록")

            // 텍스트 변환 결과 카드
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState()) // 스크롤 적용
                    .background(
                        brush = Brush.linearGradient(listOf(Color(0xFFE5DFFF), Color(0xFFDCEAFF))),
                        shape = RoundedCornerShape(22.dp)
                    )
                    .padding(20.dp)
            ) {
                entries.forEach { entry ->
                    MiniDiaryCard(
                        entry = entry,
                        onClick = { navController.navigate("diaryDetail/${entry.id}") }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Preview
@Composable
fun DiaryHistoryPagePreview() {
    val dummyEntries = listOf(
        DataDiaryEntry(date = java.util.Date(), content = "오늘은 기분이 좋았어요!", emotion = "행복", comfortMessage = "위로 멘트"),
        DataDiaryEntry(date = java.util.Date(), content = "아드님과 좋은 시간을 보내셨나봐요.", emotion = "사랑", comfortMessage = "위로 멘트"),
        DataDiaryEntry(date = java.util.Date(), content = "오늘은 혼자있는 시간이 많았나봐요", emotion = "슬픔", comfortMessage = "위로 멘트"),
        DataDiaryEntry(date = java.util.Date(), content = "내일도 행복하길 바라요~", emotion = "설렘", comfortMessage = "위로 멘트"),
        DataDiaryEntry(date = java.util.Date(), content = "오늘은 혼자있는 시간이 많았나봐요", emotion = "슬픔", comfortMessage = "위로 멘트")
    )
    HangyulTheme {
        DiaryHistoryPage(navController = rememberNavController())
    }
}