package com.android.hangyul.ui.screen.routine

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.hangyul.R

@Composable
fun AlarmListPage() {
    Column (
        modifier = Modifier
            .padding(horizontal = 20.dp)
    ){
        Text(
            text = "약 복용 알람 목록",
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 30.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_bold)),
            )
        )
    }
}

@Preview
@Composable
fun AlarmListPagePreview() {
    AlarmListPage()
}