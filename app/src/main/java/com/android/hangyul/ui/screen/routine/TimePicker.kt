package com.android.hangyul.ui.screen.routine

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.material3.TimeInput
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.hangyul.ui.theme.restorePurple


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePicker(
    onTimeSelected: (Int, Int) -> Unit
) {
    val timePickerState = rememberTimePickerState(is24Hour = false)
Column (
    modifier = Modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
){
    TimeInput(state = timePickerState)

    Spacer(modifier = Modifier.height(50.dp))
    Button(onClick = {
        val selectedHour = timePickerState.hour
        val selectedMinute = timePickerState.minute
        onTimeSelected(selectedHour, selectedMinute)
    },
        modifier = Modifier
            .width(300.dp)) {
        Text("저장")
    }
}

}

@Preview
@Composable
fun TimePickerPreview() {
    TimePicker{ hour, minute ->
        println("선택된 시간: $hour:$minute")
    }
}