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
        "분노", "툴툴대는", "좌절한", "짜증내는", "방어적인", "악의적인", "안달하는", "구역질 나는", "노여워하는", "성가신" -> "😠"
        "슬픔", "실망한", "비통한", "후회되는", "우울한", "마비된", "염세적인", "눈물이 나는", "낙담한", "환멸을 느끼는" -> "😢"
        "불안", "두려운", "스트레스 받는", "취약한", "혼란스러운", "당혹스러운", "회의적인", "걱정스러운", "조심스러운", "초조한" -> "😰"
        "상처", "질투하는", "배신당한", "고립된", "충격 받은", "가난한 불우한", "희생된", "억울한", "괴로워하는", "버려진" -> "💔"
        "당황", "고립된(당황한)", "남의 시선을 의식하는", "외로운", "열등감", "죄책감의", "부끄러운", "혼란스러운(당황한)", "한심한" -> "😅" // '한심한'도 당황으로 매핑
        "기쁨", "감사하는", "신뢰하는", "편안한", "만족스러운", "흥분", "느긋", "안도", "신이 난", "자신하는" -> "😊"
        "놀람" -> "😲"
        "혐오스러운" -> "🤢"
        else -> "😐" // 일치하는 감정이 없으면 중립 이모지
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
        Column {
            DiaryDetailPage(
                date = "2025년 6월 10일 (월)",
                convertedText = """
                오늘은 정말 행복한 하루였어요! 친구들과 맛있는 음식을 먹고 즐거운 시간을 보냈어요.
                """.trimIndent(),
                emotion = "기쁨",
                emotionComment = "오늘도 행복한 하루였네요! 내일도 좋은 일이 가득할 거예요."
            )
            Spacer(modifier = Modifier.height(16.dp))
            DiaryDetailPage(
                date = "2025년 6월 9일 (일)",
                convertedText = """
                너무 슬프고 힘든 하루였어요. 모든 것이 마음대로 되지 않아 낙담했어요.
                """.trimIndent(),
                emotion = "슬픔",
                emotionComment = "힘든 시간이 지나면 반드시 좋은 일이 찾아올 거예요. 지금은 충분히 슬퍼도 괜찮아요."
            )
            Spacer(modifier = Modifier.height(16.dp))
            DiaryDetailPage(
                date = "2025년 6월 8일 (토)",
                convertedText = """
                정말 화가 나는 일이 있었어요. 왜 이렇게 짜증나는 상황이 계속되는지 모르겠어요.
                """.trimIndent(),
                emotion = "분노",
                emotionComment = "화가 나는 일이 있었군요. 하지만 그 감정도 당연한 거예요. 천천히 진정해보세요."
            )
            Spacer(modifier = Modifier.height(16.dp))
            DiaryDetailPage(
                date = "2025년 6월 7일 (금)",
                convertedText = """
                갑자기 무서운 일이 생겨서 너무 불안했어요. 조심스러워지고 초조해져요.
                """.trimIndent(),
                emotion = "불안",
                emotionComment = "걱정하지 마세요. 모든 일이 잘 해결될 거예요. 지금은 천천히 호흡을 가다듬어보세요."
            )
            Spacer(modifier = Modifier.height(16.dp))
            DiaryDetailPage(
                date = "2025년 6월 6일 (목)",
                convertedText = """
                오늘은 평범하고 평온한 하루였어요. 특별한 일은 없었지만 차분하게 보냈어요.
                """.trimIndent(),
                emotion = "중립",
                emotionComment = "평온한 하루를 보내셨네요. 이런 날들도 소중한 추억이 될 거예요."
            )
            Spacer(modifier = Modifier.height(16.dp))
            DiaryDetailPage(
                date = "2025년 6월 5일 (수)",
                convertedText = """
                깜짝 놀랄 만한 일이 일어났어요! 예상치 못한 반전에 정말 놀랐어요.
                """.trimIndent(),
                emotion = "놀람",
                emotionComment = "놀라운 일이 있었군요! 이런 순간들이 인생을 더 특별하게 만들어요."
            )
            Spacer(modifier = Modifier.height(16.dp))
            DiaryDetailPage(
                date = "2025년 6월 4일 (화)",
                convertedText = """
                정말 역겨운 상황이었어요. 너무 싫어서 구역질이 날 것 같았어요.
                """.trimIndent(),
                emotion = "혐오스러운",
                emotionComment = "불편한 일이 있었군요. 하지만 그 감정도 당연한 거예요. 천천히 마음을 가라앉혀보세요."
            )
            Spacer(modifier = Modifier.height(16.dp))
             DiaryDetailPage(
                date = "2025년 6월 3일 (월)",
                convertedText = """
                배신당하고 버려진 느낌이 들었어요. 마음이 너무 아파요.
                """.trimIndent(),
                emotion = "상처",
                emotionComment = "당신의 감정을 이해해요. 힘든 시간이 지나면 반드시 좋은 일이 찾아올 거예요."
            )
             Spacer(modifier = Modifier.height(16.dp))
             DiaryDetailPage(
                date = "2025년 6월 2일 (일)",
                convertedText = """
                남의 시선을 의식하게 되고 부끄러운 마음에 당황스러웠어요.
                """.trimIndent(),
                emotion = "당황",
                emotionComment = "지금 느끼는 당혹감은 당연한 거예요. 괜찮아요. 이 또한 지나갈 거예요."
            )
        }
    }
}