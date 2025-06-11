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

    private fun mapEmotionToCategory(emotion: String): String {
        return when (emotion) {
            // 분노 관련 감정
            "분노", "툴툴대는", "좌절한", "짜증내는", "방어적인", "악의적인", "안달하는", "구역질 나는", "노여워하는", "성가신" -> "분노"
            
            // 슬픔 관련 감정
            "슬픔", "실망한", "비통한", "후회되는", "우울한", "마비된", "염세적인", "외로운", "눈물이 나는", "낙담한", "환멸을 느끼는" -> "슬픔"
            
            // 불안/공포 관련 감정
            "불안", "두려운", "스트레스 받는", "취약한", "혼란스러운", "당혹스러운", "회의적인", "걱정스러운", "조심스러운", "초조한" -> "공포"
            
            // 상처/혐오 관련 감정
            "상처", "질투하는", "배신당한", "고립된", "충격 받은", "가난한 불우한", "희생된", "억울한", "괴로워하는", "버려진" -> "혐오"
            
            // 당황/놀람 관련 감정
            "당황", "고립된(당황한)", "남의 시선을 의식하는", "열등감", "죄책감의", "부끄러운", "혼란스러운(당황한)", "한심한" -> "놀람"
            
            // 기쁨 관련 감정
            "기쁨", "감사하는", "신뢰하는", "편안한", "만족스러운", "흥분", "느긋", "안도", "신이 난", "자신하는" -> "행복"
            
            // 기본 감정
            "중립" -> "중립"
            "놀람" -> "놀람"
            "혐오스러운" -> "혐오"
            else -> "중립"
        }
    }

    fun addDiaryEntry(content: String) {
        viewModelScope.launch {
            try {
                // 감정 분석
                val emotion = emotionAnalyzer.analyzeEmotion(content)

                // 감정을 카테고리로 매핑
                val emotionCategory = mapEmotionToCategory(emotion)
                
                // EmotionCommentProvider를 사용하여 감정에 따른 위로 문구 생성
                val comfortMessage = EmotionCommentProvider.getRandomComment(emotionCategory)

                val entry = DiaryEntry(
                    date = Date(),
                    content = content,
                    emotion = emotion,
                    comfortMessage = comfortMessage
                )

                withContext(Dispatchers.IO) {
                    db.collection("diaries")
                        .add(entry)
                        .addOnSuccessListener {
                            Log.d("DiaryViewModel", "일기 저장 성공")
                        }
                        .addOnFailureListener { e ->
                            Log.e("DiaryViewModel", "일기 저장 실패", e)
                        }
                }
            } catch (e: Exception) {
                Log.e("DiaryViewModel", "일기 저장 중 오류 발생", e)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        emotionAnalyzer.close()
    }
}
