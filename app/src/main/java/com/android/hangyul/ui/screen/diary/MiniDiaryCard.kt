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
        Column {
            MiniDiaryCard(
                entry = DataDiaryEntry(
                    date = Date(),
                    emotion = "행복",
                    content = "오늘은 정말 행복한 하루였어요!",
                    comfortMessage = "위로 멘트"
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            MiniDiaryCard(
                entry = DataDiaryEntry(
                    date = Date(),
                    emotion = "분노",
                    content = "정말 화가 나는 일이 있었어요.",
                    comfortMessage = "위로 멘트"
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            MiniDiaryCard(
                entry = DataDiaryEntry(
                    date = Date(),
                    emotion = "슬픔",
                    content = "너무 슬프고 힘든 하루였어요.",
                    comfortMessage = "위로 멘트"
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            MiniDiaryCard(
                entry = DataDiaryEntry(
                    date = Date(),
                    emotion = "불안",
                    content = "갑자기 무서운 일이 생겨서 불안했어요.",
                    comfortMessage = "위로 멘트"
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            MiniDiaryCard(
                entry = DataDiaryEntry(
                    date = Date(),
                    emotion = "놀람",
                    content = "깜짝 놀랄 만한 일이 일어났어요!",
                    comfortMessage = "위로 멘트"
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            MiniDiaryCard(
                entry = DataDiaryEntry(
                    date = Date(),
                    emotion = "혐오스러운",
                    content = "정말 역겨운 상황이었어요.",
                    comfortMessage = "위로 멘트"
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            MiniDiaryCard(
                entry = DataDiaryEntry(
                    date = Date(),
                    emotion = "상처",
                    content = "배신당한 느낌이 들었어요.",
                    comfortMessage = "위로 멘트"
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            MiniDiaryCard(
                entry = DataDiaryEntry(
                    date = Date(),
                    emotion = "당황",
                    content = "남의 시선을 의식하게 되어 당황스러웠어요.",
                    comfortMessage = "위로 멘트"
                )
            )
        }
    }
}

private fun getEmojiForEmotion(emotion: String): String {
    return when (emotion.lowercase()) {
        "분노", "툴툴대는", "좌절한", "짜증내는", "방어적인", "악의적인", "안달하는", "구역질 나는", "노여워하는", "성가신" -> "😠"
        "슬픔", "실망한", "비통한", "후회되는", "우울한", "마비된", "염세적인", "눈물이 나는", "낙담한", "환멸을 느끼는" -> "😢"
        "불안", "두려운", "스트레스 받는", "취약한", "혼란스러운", "당혹스러운", "회의적인", "걱정스러운", "조심스러운", "초조한" -> "😰"
        "상처", "질투하는", "배신당한", "고립된", "충격 받은", "가난한 불우한", "희생된", "억울한", "괴로워하는", "버려진" -> "💔"
        "당황", "고립된(당황한)", "남의 시선을 의식하는", "외로운", "열등감", "죄책감의", "부끄러운", "혼란스러운(당황한)", "한심한" -> "😅"
        "기쁨", "감사하는", "신뢰하는", "편안한", "만족스러운", "흥분", "느긋", "안도", "신이 난", "자신하는" -> "😊"
        "놀람" -> "😲"
        "혐오스러운" -> "🤢"
        else -> "😐" // 일치하는 감정이 없으면 중립 이모지
    }
}