package com.android.hangyul.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.hangyul.R
import com.android.hangyul.ui.theme.Blue80
import com.android.hangyul.ui.theme.Purple40
import com.android.hangyul.ui.theme.Purple80


@Composable
fun ListBtn(icon: Int,
            title: String,
            date: String,
            modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth(0.8f)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Purple80, Blue80)),
                shape = RoundedCornerShape(size=20.dp))
            .padding(horizontal = 16.dp, vertical = 18.dp),
        contentAlignment = Alignment.Center
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = "그림",
                modifier = Modifier.padding(start = 20.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 30.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_bold)),
                        color = Color.Black,
                    )
                )
                Text(
                    text = date,
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 15.sp,
                        fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                        fontWeight = FontWeight(600),
                        color = Purple40,
                    )
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_list_next),
                contentDescription = "이동 버튼",
                modifier = Modifier.size(30.dp)
            )
        }
    }
}
