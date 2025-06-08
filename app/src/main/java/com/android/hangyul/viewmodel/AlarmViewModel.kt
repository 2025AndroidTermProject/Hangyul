package com.android.hangyul.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

data class AlarmItem(
    val id: String = UUID.randomUUID().toString(),
    val hour: Int,
    val minute: Int,
    val medicineName: String
)

fun AlarmItem.toMap(): Map<String, Any> = mapOf(
    "id" to id,
    "hour" to hour,
    "minute" to minute,
    "medicineName" to medicineName
)

class AlarmViewModel : ViewModel() {

    private val db = Firebase.firestore

    var hour by mutableStateOf(0)
    var minute by mutableStateOf(0)
    var medicineName by mutableStateOf("")

    private val _alarms = MutableStateFlow<List<AlarmItem>>(emptyList())
    val alarms = _alarms.asStateFlow()

    fun uploadAlarmToFirestore(alarm: AlarmItem) {
        db.collection("alarms")
            .document(alarm.id)
            .set(alarm.toMap())
            .addOnSuccessListener {
                Log.d("AlarmDebug", "알람 저장 성공: ${alarm.id}")
            }
            .addOnFailureListener {
                Log.e("AlarmDebug", "알람 저장 실패", it)
            }
    }

    fun addAlarm() {
        val alarm = AlarmItem(
            hour = hour,
            minute = minute,
            medicineName = medicineName,
        )
        _alarms.value = _alarms.value + alarm

        uploadAlarmToFirestore(alarm)
        reset()
    }

    fun deleteAlarm(id: String) {
        _alarms.value = _alarms.value.filterNot { it.id == id }
        db.collection("alarms").document(id).delete()
    }

    fun fetchAlarms() {
        db.collection("alarms")
            .get()
            .addOnSuccessListener { result ->
                val alarmList = result.mapNotNull { doc ->
                    val hour = doc.getLong("hour")?.toInt()
                    val minute = doc.getLong("minute")?.toInt()
                    val name = doc.getString("medicineName")
                    val id = doc.getString("id")
                    if (hour != null && minute != null && name != null && id != null) {
                        AlarmItem(id, hour, minute, name)
                    } else null
                }
                _alarms.value = alarmList
                Log.d("AlarmDebug", "가져온 알람 수: ${alarmList.size}")
            }
    }


    fun reset() {
        hour = 0
        minute = 0
        medicineName = ""
    }
}