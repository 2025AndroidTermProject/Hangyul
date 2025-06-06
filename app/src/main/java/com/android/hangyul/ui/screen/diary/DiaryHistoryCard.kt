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

data class DiaryEntry(
    val date: String,
    val emoji: String,
    val emotion: String,
    val content: String
)
@Composable
fun DiaryHistoryCard(
    entries: List<DiaryEntry>,
    modifier: Modifier = Modifier,
    onHeaderClick: () ->Unit = {},
    onEntryClick: (DiaryEntry) -> Unit = {}
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White.copy(alpha = 0.5f))
                    .clickable { onEntryClick(entry) }
                    .padding(12.dp)
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50))
                            .background(Color.White.copy(alpha=0.75f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "🎙️", fontSize = 14.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(

                                text = entry.date,
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    lineHeight = 30.sp,
                                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF000000)
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row {
                        Text(text = entry.emoji, fontSize = 18.sp)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            modifier = Modifier.align(Alignment.CenterVertically),
                            text = "${entry.emotion}  |  “${entry.content}”",
                            style = TextStyle(
                                fontSize = 13.sp,
                                lineHeight = 30.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                                fontWeight = FontWeight(500),
                                color = Color(0xFF000000),

                                )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
@Preview(showBackground = true)
@Composable
fun DiaryHistoryCardPreview() {
    val dummyEntries = listOf(
        DiaryEntry("5월 26일", "😊", "행복", "오늘은 기분이 좋았어요!"),
        DiaryEntry("5월 25일", "🥺", "슬픔", "오늘은 혼자있는 시간이 많았나봐요")
    )

    HangyulTheme {
        DiaryHistoryCard(
            entries = dummyEntries,
            onHeaderClick = {
                println("헤더 클릭됨 - history 페이지 이동")
            },
            onEntryClick = { clicked ->
                println("클릭된 일기: ${clicked.date}")
            }
        )
    }
}
