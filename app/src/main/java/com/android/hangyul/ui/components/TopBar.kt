package com.android.hangyul.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.hangyul.R
import com.android.hangyul.ui.theme.Blue
import com.android.hangyul.ui.theme.Purple

@Composable
fun TopBar(pageName:String) {
    Box(
        modifier = Modifier
            .width(360.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Purple, Blue)
                )
            )
            .padding(start = 35.dp, top = 55.dp,)
    )
    {

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = pageName,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_bold)),
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )

            Image(
                modifier = Modifier
                    .padding(end=20.dp)
                    .width(20.dp)
                    .height(25.dp),
                painter = painterResource(id = R.drawable.ic_top_options), // PNG 넣은 파일명
                contentDescription = "optionBar",
            )
        }
    }
}

@Preview
@Composable
fun TopBarPreview() {
    TopBar("음성일기")
}
