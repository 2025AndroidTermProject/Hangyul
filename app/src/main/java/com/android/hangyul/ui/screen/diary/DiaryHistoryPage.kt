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

@Composable
fun DiaryHistoryPage(
    entries: List<DiaryEntry>,
    onEntryClick: (DiaryEntry) -> Unit = {}
) {
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
                    MiniDiaryCard(entry = entry, onClick = { onEntryClick(entry) })
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
            DiaryEntry("5월 26일", "😊", "행복", "오늘은 기분이 좋았어요!","위로 멘트"),
            DiaryEntry("5월 25일", "💖", "사랑", "아드님과 좋은 시간을 보내셨나봐요.","위로 멘트"),
            DiaryEntry("5월 24일", "😢", "슬픔", "오늘은 혼자있는 시간이 많았나봐요","위로 멘트"),
            DiaryEntry("5월 23일", "💖", "설렘", "내일도 행복하길 바라요~","위로 멘트"),
            DiaryEntry("5월 22일", "😢", "슬픔", "오늘은 혼자있는 시간이 많았나봐요","위로 멘트")
        )
        HangyulTheme {
            DiaryHistoryPage(entries = dummyEntries)
        }
    }