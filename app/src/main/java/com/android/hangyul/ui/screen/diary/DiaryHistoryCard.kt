package com.android.hangyul.ui.screen.diary

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.hangyul.R
import com.android.hangyul.ui.theme.HangyulTheme
import com.android.hangyul.data.DiaryEntry as DataDiaryEntry // Import Firebase's DiaryEntry
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date

@Composable
fun DiaryHistoryCard(
    entries: List<DataDiaryEntry>,
    modifier: Modifier = Modifier,
    onHeaderClick: () ->Unit = {}, // 헤더(제목) 클릭 시 호출
    onEntryClick: (DataDiaryEntry) -> Unit = {} // 엔트리(기록 카드) 클릭 시 호출
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFE5DFFF), Color(0xFFDCEAFF))
                )
            )
            .padding(16.dp)
    ) {
        Text(
            text = "지난 일기 기록 보기 >",
            modifier = Modifier
                .clickable{onHeaderClick()},
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 30.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                fontWeight = FontWeight(700),
                color = Color(0xFF000000),
                )
        )
        Spacer(modifier = Modifier.height(12.dp))
        entries.forEach { entry ->
            MiniDiaryCard(
                entry = entry,
                onClick = { onEntryClick(entry) },
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}
@Preview(showBackground = true)
@Composable
fun DiaryHistoryCardPreview() {
    val dummyEntries = listOf(
        DataDiaryEntry(date = java.util.Date(), content = "오늘은 기분이 좋았어요!", emotion = "행복", comfortMessage = ""),
        DataDiaryEntry(date = java.util.Date(), content = "오늘은 혼자있는 시간이 많았나봐요", emotion = "슬픔", comfortMessage = "")
    )

    HangyulTheme {
        DiaryHistoryCard(
            entries = dummyEntries,
            onHeaderClick = {
                println("헤더 클릭됨 - history 페이지 이동")
            },
            onEntryClick = { clicked ->
                println("클릭된 일기: ${clicked.id}") // Use ID for logging consistency
            }
        )
    }
}
