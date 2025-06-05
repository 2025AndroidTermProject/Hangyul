package com.android.hangyul.ui.screen.routine

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.hangyul.R
import com.android.hangyul.ui.theme.Purple80

@Composable
fun AlarmList(hour: Int, min: Int, text: String) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Purple80)
            .padding(vertical = 20.dp),

        verticalAlignment = Alignment.CenterVertically
    ){
        Spacer(modifier = Modifier.width(25.dp))
        Image(
            painter = painterResource(R.drawable.ic_routine_alarm),
            contentDescription = "약 복용 알람 아이콘",
            modifier = Modifier
                .size(35.dp),
        )
        Spacer(modifier = Modifier.width(15.dp))

        Column {
            Text(
                text = "${hour}:${min}",
                style = TextStyle(
                    fontSize = 35.sp,
                    lineHeight = 24.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_extrabold)),
                    textAlign = TextAlign.Center,
                )
            )
            Text(
                text = text,
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 24.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                    textAlign = TextAlign.Center,
                )
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(R.drawable.ic_alarm_delete),
            contentDescription = "알람 삭제 버튼",
            modifier = Modifier
                .padding(end = 30.dp)
                .size(40.dp)

        )
    }
}

@Preview
@Composable
fun AlarmListPreview() {
    AlarmList(13,30,"오메가3")
}