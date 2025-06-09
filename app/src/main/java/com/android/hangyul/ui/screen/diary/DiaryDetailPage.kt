package com.android.hangyul.ui.screen.diary

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.hangyul.R
import com.android.hangyul.ui.components.TopBar
import com.android.hangyul.ui.theme.HangyulTheme
import com.android.hangyul.ui.theme.Purple80

private fun getEmotionEmoji(emotion: String): String {
    return when (emotion.lowercase()) {
        "기쁨", "행복" -> "😊"
        "슬픔" -> "😢"
        "분노" -> "😠"
        "불안" -> "😰"
        "중립" -> "😐"
        "놀람" -> "😲"
        "혐오" -> "🤢"
        else -> "😊"
    }
}

@Composable
fun DiaryDetailPage(
    date: String,
    convertedText: String,
    emotion: String,
    emotionComment: String,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .background(Color(0xFFF1F0FF))
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            // 날짜 + 말풍선 아이콘
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.diary_chatbubble),
                    contentDescription = "image description",
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.height(60.dp).aspectRatio(1.15f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = date,
                    fontSize = 27.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_bold)),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 텍스트 변환 결과 카드
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(listOf(Color(0xFFE5DFFF), Color(0xFFDCEAFF))),
                        shape = RoundedCornerShape(22.dp)
                    )
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "텍스트 변환 결과",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_bold))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = convertedText,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                        lineHeight = 22.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 감정 분석 카드
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.linearGradient(
                            listOf(
                                Color(0xFFE5DFFF),
                                Color(0xFFDCEAFF)
                            )
                        ),
                        shape = RoundedCornerShape(22.dp)
                    )
                    .padding(20.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "한결이 감정 분석 결과",
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 30.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard_bold)),
                            fontWeight = FontWeight(700),
                            color = Color(0xFF000000)
                        )
                    )
                    
                    Spacer(modifier = Modifier.height(12.dp))
                    Box(
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(40.dp))
                            .padding(horizontal = 24.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "${getEmotionEmoji(emotion)} $emotion",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = emotionComment,
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                        lineHeight = 22.sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DiaryDetailPagePreview() {
    HangyulTheme {
        DiaryDetailPage(
            date = "2025년 5월 27일 (화)",
            convertedText = """
                오늘은 우리 아들과 낚시를 갔다. 점성어를 낚았다.낚시 후에는 맛있는 매운탕을 먹으러 갔다.
                울 아들램이 사줘서 더 맛있다. 울 아들 고마워~.
                너무 좋은 시간이었다.
            """.trimIndent(),
            emotion = "행복",
            emotionComment = "오늘은 기분이 좋았어요!\n내일도 행복한 날일거예요!"
        )
    }
}