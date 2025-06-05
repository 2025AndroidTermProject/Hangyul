package com.android.hangyul.ui.screen.routine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.hangyul.R
import com.android.hangyul.ui.theme.Gray80

@Composable
fun AlarmAddPage() {
    Column (
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(35.dp))
        Text(
            text = "약 복용 설정",
            modifier = Modifier.padding(start = 25.dp),
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 30.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_bold)),
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = "", // 상태로 연결 예정
            onValueChange = {},
            label = { Text("알람 이름") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )


    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xF1F0FF
)
@Composable
fun AlarmAddPagePreview() {
    AlarmAddPage()
}