package com.android.hangyul.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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

    val db = Firebase.firestore

    private val _memories = MutableStateFlow<List<Memory>>(emptyList())
    val memories = _memories.asStateFlow()

    var imageUrl by mutableStateOf<String?>(null)
        private set

    fun addImage(url: String) {
        imageUrl = url
    }

    fun addMemory(memory: Memory) {
        _memories.value = _memories.value + memory

        db.collection("memories").document(memory.id).set(memory)
            .addOnSuccessListener {
                Log.d("MemoryDebug", "추억 저장 성공: ${memory.id}")
            }
            .addOnFailureListener {
                Log.e("MemoryDebug", "추억 저장 실패", it)
            }
    }

    fun fetchMemories() {
        db.collection("memories")
            .get()
            .addOnSuccessListener { result ->
                val memoryList = result.mapNotNull { doc ->
                    val id = doc.getString("id")
                    val title = doc.getString("title")
                    val date = doc.getString("date")
                    val content = doc.getString("content")
                    val imageUrl = doc.getString("imageUrl")
                    if (id != null && title != null && date != null && content != null) {
                        Memory(id, title, date, content, imageUrl)
                    } else null
                }
                _memories.value = memoryList
            }
            .addOnFailureListener {
                Log.e("MemoryDebug", "추억 불러오기 실패", it)
            }
    }

    fun clearInputs() {
        imageUrl = null
    }
}
