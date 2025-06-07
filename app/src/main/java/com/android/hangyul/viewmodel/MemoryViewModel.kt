package com.android.hangyul.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

data class Memory(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val date: String,
    val content: String,
    val imageUrl: String? = null
)

class MemoryViewModel : ViewModel() {
    private val _memories = MutableStateFlow<List<Memory>>(emptyList())
    val memories = _memories.asStateFlow()

    var imageUrl by mutableStateOf<String?>(null)  // 현재 업로드된 이미지 하나
        private set

    fun addImage(url: String) {
        imageUrl = url
    }

    fun addMemory(memory: Memory) {
        _memories.value = _memories.value + memory
    }

    fun clearInputs() {
        imageUrl = null
    }
}
