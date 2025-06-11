package com.android.hangyul.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.android.hangyul.data.DiaryEntry
import com.android.hangyul.ml.EmotionAnalyzer
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

                // 감정에 따른 위로 문구 생성
                val comfortMessage = when (emotion) {
                    // 분노 관련 감정
                    "분노", "툴툴대는", "좌절한", "짜증내는", "방어적인", "악의적인", "안달하는", "구역질 나는", "노여워하는", "성가신" ->
                        "화가 나는 일이 있었군요. 하지만 그 감정도 당연한 거예요. \n천천히 진정해보세요."
                    
                    // 슬픔 관련 감정
                    "슬픔", "실망한", "비통한", "후회되는", "우울한", "마비된", "염세적인", "눈물이 나는", "낙담한", "환멸을 느끼는" ->
                        "힘든 시간이 지나면 반드시 좋은 일이 찾아올 거예요. \n지금은 충분히 슬퍼도 괜찮아요."
                    
                    // 불안 관련 감정
                    "불안", "두려운", "스트레스 받는", "취약한", "혼란스러운", "당혹스러운", "회의적인", "걱정스러운", "조심스러운", "초조한" ->
                        "걱정하지 마세요. 모든 일이 잘 해결될 거예요. \n지금은 천천히 호흡을 가다듬어보세요."
                    
                    // 상처 관련 감정
                    "상처", "질투하는", "배신당한", "고립된", "충격 받은", "가난한 불우한", "희생된", "억울한", "괴로워하는", "버려진" ->
                        "당신의 감정을 이해해요. \n힘든 시간이 지나면 반드시 좋은 일이 찾아올 거예요."
                    
                    // 당황 관련 감정
                    "당황", "고립된(당황한)", "남의 시선을 의식하는", "외로운", "열등감", "죄책감의", "부끄러운", "혼란스러운(당황한)", "한심한" ->
                        "지금 느끼는 그 감정은 당연한 거예요. \n괜찮아요. 이 또한 지나갈 거예요."
                    
                    // 기쁨 관련 감정
                    "기쁨", "감사하는", "신뢰하는", "편안한", "만족스러운", "흥분", "느긋", "안도", "신이 난", "자신하는" ->
                        "오늘도 행복한 하루였네요! \n내일도 좋은 일이 가득할 거예요."
                    
                    // 놀람 관련 감정
                    "놀람" -> "놀라운 일이 있었군요! \n이런 순간들이 인생을 더 특별하게 만들어요."
                    
                    // 혐오 관련 감정
                    "혐오스러운" -> "불편한 일이 있었군요. 하지만 그 감정도 당연한 거예요. \n천천히 마음을 가라앉혀보세요."
                    
                    // 중립 관련 감정
                    "중립" -> "평온한 하루를 보내셨네요. \n이런 날들도 소중한 추억이 될 거예요."
                    
                    else -> "오늘 하루도 수고하셨어요. \n내일은 더 좋은 하루가 될 거예요."
                }


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