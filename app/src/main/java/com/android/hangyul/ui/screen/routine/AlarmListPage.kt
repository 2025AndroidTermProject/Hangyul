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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.hangyul.R
import com.android.hangyul.ui.components.AddBtn
import com.android.hangyul.viewmodel.AlarmItem
import com.android.hangyul.viewmodel.AlarmViewModel

@Composable
fun AlarmListPage(navController: NavController, viewModel: AlarmViewModel) {
    val alarms by viewModel.alarms.collectAsState()

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

        Spacer(modifier = Modifier.height(20.dp))

        alarms.forEach { alarm ->
            AlarmList(
                hour = alarm.hour,
                min = alarm.minute,
                text = alarm.medicineName,
                onDelete = { viewModel.deleteAlarm(alarm.id) }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }


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

