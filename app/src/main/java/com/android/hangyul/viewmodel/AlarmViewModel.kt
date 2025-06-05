package com.android.hangyul.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AlarmViewModel : ViewModel() {
    var alarmName by mutableStateOf("")
    var medicineName by mutableStateOf("")
    var hour by mutableStateOf(0)
    var minute by mutableStateOf(0)


    fun reset() {
        alarmName = ""
        medicineName = ""
        hour = 0
        minute = 0
    }
}