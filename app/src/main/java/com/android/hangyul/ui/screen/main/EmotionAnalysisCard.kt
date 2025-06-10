package com.android.hangyul.ui.screen.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.hangyul.R
import com.android.hangyul.ui.theme.HangyulTheme

private fun getEmotionEmoji(emotion: String): String {
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

@Composable
fun EmotionAnalysisCard(
    analysisText: String,
    encouragementMsg: String,
    date:String,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = Modifier
            .height(173.dp)
            .background(color = Color(0x40FFFFFF), shape = RoundedCornerShape(20.dp))
            .padding(18.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // 타이틀
            Text(
                text = "오늘의 감정 분석 결과 ",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.pretendard_semibold))
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 감정 분석 결과 (이모지 + 텍스트)
            Box(
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.8f), RoundedCornerShape(40.dp))
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            ) {
                Text(
                    text = "${getEmotionEmoji(analysisText)} $analysisText",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 위로 격려 메시지
            Text(
                text = encouragementMsg,
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_medium))
            )
        }

        // 날짜 오른쪽 하단
//        Text(
//            text = date,
//            color = Color.White,
//            fontSize = 15.sp,
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .padding(bottom = 0.dp, end = 4.dp),
//            fontFamily = FontFamily(Font(R.font.pretendard_semibold))
//        )
    }
}

@Preview
@Composable
fun EmotionAnalysisCardPreview() {
    HangyulTheme {
        EmotionAnalysisCard(
            "오늘은 조금 지친 마음이 느껴졌어요",
            "오늘도 잘 버텼어요. 마음이 괜찮아 질거예요.",
            "5/27(화)",
            onClick = {},
            modifier = Modifier
        )
    }
}