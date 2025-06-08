package com.android.hangyul.ui.screen.routine

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.hangyul.R
import com.android.hangyul.ui.components.AddInputField
import com.android.hangyul.viewmodel.AlarmViewModel

@Composable
fun AlarmAddPage(viewModel: AlarmViewModel, onSave: ()->Unit) {

    Column (
        modifier = Modifier
            .fillMaxSize(),
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

        Spacer(modifier = Modifier.height(20.dp))

        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.height(10.dp))
            AddInputField(
                text = viewModel.medicineName,
                onTextChanged = { viewModel.medicineName = it },
                placeholderText = "약 이름")
        }

        Spacer(modifier = Modifier.height(35.dp))

        Text(
            text = "시간",
            modifier = Modifier.padding(start = 25.dp),
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 30.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_bold)),
            )
        )

        Spacer(modifier = Modifier.height(40.dp))

        TimePicker { hour, minute ->
            viewModel.hour = hour
            viewModel.minute = minute
            viewModel.addAlarm()
            onSave()
        }



    }
}
