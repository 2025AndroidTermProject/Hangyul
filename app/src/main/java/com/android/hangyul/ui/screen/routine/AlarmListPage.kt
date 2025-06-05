package com.android.hangyul.ui.screen.routine

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
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
import com.android.hangyul.ui.components.AddBtn

@Composable
fun AlarmListPage(navController: NavController) {
    Column (
        modifier = Modifier
            .fillMaxSize()
    ){
        Spacer(modifier = Modifier.height(35.dp))
        Text(
            text = "약 복용 알람 목록",
            modifier = Modifier.padding(start = 25.dp),
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 30.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_bold)),
            )
        )

        Spacer(modifier = Modifier.height(22.dp))

        AlarmList(13,30,"오메가3")
        Spacer(modifier = Modifier.height(10.dp))
        AlarmList(18, 30, "비타민")

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 25.dp, bottom = 25.dp),
            horizontalArrangement = Arrangement.End
        ) {
            AddBtn("알람 등록", modifier = Modifier.clickable { navController.navigate("alarmAdd") })
        }
    }

}

