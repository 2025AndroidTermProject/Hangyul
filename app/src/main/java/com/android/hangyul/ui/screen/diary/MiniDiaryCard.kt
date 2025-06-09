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
import com.android.hangyul.data.DiaryEntry as DataDiaryEntry
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date


@Composable
fun MiniDiaryCard(
    entry: DataDiaryEntry,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White.copy(alpha = 0.5f))
            .clickable { onClick() }
            .padding(12.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(Color.White.copy(alpha = 0.75f))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "🎙️", fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = SimpleDateFormat("M월 d일", Locale.KOREAN).format(entry.date),
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
                Text(text = getEmojiForEmotion(entry.emotion), fontSize = 18.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = "${entry.emotion}  |  “${entry.content}”",
                    style = TextStyle(
                        fontSize = 13.sp,
                        lineHeight = 30.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000)
                    )
                )
            }
        }
    }
}
@Preview
@Composable
fun SimpleDiaryCardPreview() {
    HangyulTheme {
        MiniDiaryCard(
            entry = DataDiaryEntry(
                date = Date(), // Use current date for preview
                emotion = "행복",
                content = "오늘은 기분이 좋았어요!",
                comfortMessage = "위로 멘트"
            )
        )
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