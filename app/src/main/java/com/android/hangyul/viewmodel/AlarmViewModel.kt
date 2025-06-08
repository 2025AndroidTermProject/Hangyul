package com.android.hangyul.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

data class AlarmItem(
    val id: String = UUID.randomUUID().toString(),
    val hour: Int,
    val minute: Int,
    val medicineName: String
)

class AlarmViewModel : ViewModel() {
    var hour by mutableStateOf(0)
    var minute by mutableStateOf(0)
    var medicineName by mutableStateOf("")

    private val _alarms = MutableStateFlow<List<AlarmItem>>(emptyList())
    val alarms = _alarms.asStateFlow()

    fun addAlarm() {
        val alarm = AlarmItem(
            hour = hour,
            minute = minute,
            medicineName = medicineName,
        )
        _alarms.value = _alarms.value + alarm
        reset()
    }

    fun deleteAlarm(id: String) {
        _alarms.value = _alarms.value.filterNot { it.id == id }
    }

    fun reset() {
        hour = 0
        minute = 0
        medicineName = ""
    }
}