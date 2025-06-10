package com.android.hangyul.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.android.hangyul.data.DiaryEntry
import com.android.hangyul.ml.EmotionAnalyzer
import com.android.hangyul.util.EmotionCommentProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class DiaryViewModel(application: Application) : AndroidViewModel(application) {
    private val db = FirebaseFirestore.getInstance()
    // ✅ lazy 초기화로 변경 (ANR 방지)
    private val emotionAnalyzer by lazy {
        EmotionAnalyzer(application)
    }

    private val _allEntries = MutableStateFlow<List<DiaryEntry>>(emptyList())
    val allEntries: StateFlow<List<DiaryEntry>> = _allEntries.asStateFlow()

    init {
        loadDiaryEntries()
    }

    private fun loadDiaryEntries() {
        db.collection("diaries")
            .orderBy("date", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val entries = snapshot.documents.mapNotNull { doc ->
                        val id = doc.id
                        val date = doc.getTimestamp("date")?.toDate() ?: java.util.Date()
                        val content = doc.getString("content") ?: ""
                        val emotion = doc.getString("emotion") ?: ""
                        val comfortMessage = doc.getString("comfortMessage") ?: ""
                        DiaryEntry(id, date, content, emotion, comfortMessage)
                    }
                    _allEntries.value = entries
                }
            }
    }

    fun addDiaryEntry(content: String) {
        viewModelScope.launch {
            // ✅ 백그라운드 스레드에서 감정 분석 실행
            val emotion = withContext(Dispatchers.Default) {

                emotionAnalyzer.analyzeEmotion(content)
            }
            val comfortMessage = EmotionCommentProvider.getRandomComment(emotion)
            
            val entry = DiaryEntry(
                date = Date(),
                content = content,
                emotion = emotion,
                comfortMessage = comfortMessage
            )
            
            db.collection("diaries")
                .add(entry)
                .addOnSuccessListener { documentReference ->
                    // Optionally, update the ID in the local model if needed
                    // entry.id = documentReference.id (requires mutable id or re-creation)
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        emotionAnalyzer.close()
    }
} 