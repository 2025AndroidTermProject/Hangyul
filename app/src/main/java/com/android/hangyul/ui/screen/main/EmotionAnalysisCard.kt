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
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = Color(0x40000000),
                ambientColor = Color(0x40000000))
            .fillMaxWidth()
            .height(173.dp)
            .background(color = Color(0x40FFFFFF), shape = RoundedCornerShape(20.dp))
            .padding(18.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 타이틀
            Text(
                text = "오늘의 감정 분석 결과",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.pretendard_bold))
            )

            // 감정 분석 결과 (아이콘 + 텍스트)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.ic_main_report),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = analysisText,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_medium))
                )
            }

            // 위로 격려 메시지  (아이콘 + 텍스트)
            Row(verticalAlignment = Alignment.Top) {
                Image(
                    painter = painterResource(id = R.drawable.ic_main_bubble),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                    Text(
                        text = encouragementMsg,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_medium))
                    )

                }
            }
        }

        // 날짜 오른쪽 하단
        Text(
            text = date,
            color = Color.White,
            fontSize = 15.sp,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 0.dp, end = 4.dp),
            fontFamily = FontFamily(Font(R.font.pretendard_semibold))
        )
    }
}
@Preview
@Composable
fun EmotionAnalysisCardPreview(){
    HangyulTheme {
        EmotionAnalysisCard(
            "오늘은 조금 지친 마음이 느껴졌어요",
            "오늘도 잘 버텼어요. 마음이 괜찮아 질거예요.",
            "5/27(화)",
            onClick = {},
            modifier = Modifier)
    }
}