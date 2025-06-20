package com.android.hangyul.ui.screen.diary

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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

@Composable
fun ConvertedDiaryCard(
    hasTodayEntry: Boolean, // 오늘 일기가 작성되었는지 여부
    formattedDate: String, // 오늘 날짜 텍스트
    modifier: Modifier = Modifier,
    onClick: () -> Unit // 클릭 이벤트 추가
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
            .clickable { onClick() } // 클릭 처리
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = "글로 변환된 오늘의 일기 보러가기 >",
            style = TextStyle(
                fontSize = 15.sp,
                lineHeight = 30.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                fontWeight = FontWeight(700),
                color = Color(0xFF000000),

                )
        )

        Spacer(modifier = Modifier.height(13.dp))

        if (!hasTodayEntry) {
            Text(
                text = "아직 작성된 오늘의 일기가 없어요",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 30.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    fontWeight = FontWeight(600),
                    color = Color.Black,
                )
            )
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = formattedDate,
                    style = TextStyle(
                        fontSize = 15.sp,
                        lineHeight = 30.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                        fontWeight = FontWeight(600),
                        color = Color.Black,
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "New!",
                    style = TextStyle(
                        fontSize = 15.sp,
                        lineHeight = 30.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                        fontWeight = FontWeight(600),
                        color = Color.Red,
                    )
                )
            }
        }
    }
}
@Composable
@Preview(showBackground = true)
fun ConvertedDiaryCardPreview(){
    HangyulTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            ConvertedDiaryCard(hasTodayEntry = false, formattedDate = "", onClick = { println("Empty 클릭") })
            Spacer(modifier = Modifier.height(16.dp))
            ConvertedDiaryCard(hasTodayEntry = true, formattedDate = "2025년 6월 9일", onClick = { println("오늘 일기 보기로 이동") })
        }
    }
}