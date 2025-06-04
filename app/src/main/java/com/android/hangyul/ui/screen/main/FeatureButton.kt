package com.android.hangyul.ui.screen.main

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
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
import com.android.hangyul.ui.theme.HangyulTheme

@Composable
fun FeatureButton(
    @DrawableRes imageRes: Int,
    buttonTitle: String,
    buttonText: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(141.dp)
            .height(141.dp)
            .padding(4.dp) // 그림자 공간 확보
    ) {
        // 그림자 역할 Box (오른쪽 아래로 살짝 이동)
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(x = 4.dp, y = 4.dp)
                .background(
                    color = Color(0x11000000), // 25% 투명한 검정
                    shape = RoundedCornerShape(20.dp)
                )
        )
        Column(
            modifier = Modifier
                .shadow(
                    elevation = 16.dp,
                    spotColor = Color(0x40000000),
                    ambientColor = Color(0x40000000)
                )
                .padding(0.5.dp)
                .width(139.dp)
                .height(131.dp)
                .background(color = Color(0xE5FFFFFF), shape = RoundedCornerShape(size = 20.dp))
                .clickable { onClick() }
                .padding(vertical = 18.dp, horizontal = 14.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = buttonTitle,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.pretendard_bold)),
                    color = Color(0xFF1C1C1C)
                )
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = buttonText,
                maxLines = 1,
                style = TextStyle(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                    color = Color(0xFF7E7E7E)
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeatureButtonPreview() {
    HangyulTheme {
        FeatureButton(
            imageRes = R.drawable.ic_main_memory,
            buttonTitle = "추억 기록",
            buttonText = "소중한 순간을 기억해요.",
            onClick = {}
        )
    }
}