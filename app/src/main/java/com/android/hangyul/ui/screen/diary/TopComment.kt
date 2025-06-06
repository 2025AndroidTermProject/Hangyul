package com.android.hangyul.ui.screen.diary

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun TopComment(){
    Row(
        horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.Start),
        verticalAlignment = Alignment.Top,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.diary_chatbubble),
            contentDescription = "image description",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.height(60.dp).aspectRatio(1.15f)

        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "오늘의 하루를 목소리로\n기록해봐요.",
            style = TextStyle(
                fontSize = 25.sp,
                lineHeight = 30.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                fontWeight = FontWeight(600),
                color = Color(0xFF000000),

                )
        )
    }
}

@Composable
@Preview(showBackground = true)
fun TopCommentPreview(){
    HangyulTheme {
        TopComment()
    }
}