package com.android.hangyul.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MemoryViewModel : ViewModel() {
    var title by mutableStateOf("")
    var date by mutableStateOf("")
    var content by mutableStateOf("")
    var images by mutableStateOf(listOf<String>())

    fun saveMemory() {
        println("저장 완료: $title / $date / $content / $images")
    }

    fun addImage(path: String) {
        images = images + path
    }
}