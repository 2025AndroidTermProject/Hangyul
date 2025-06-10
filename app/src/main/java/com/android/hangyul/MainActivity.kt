package com.android.hangyul

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import com.android.hangyul.navigation.NavGraph
import androidx.navigation.compose.rememberNavController
import com.android.hangyul.ml.EmotionAnalyzer
import com.android.hangyul.ui.theme.HangyulTheme
import com.android.hangyul.ui.components.NaviBar
import com.android.hangyul.ui.components.TopBar
import com.google.android.libraries.places.api.Places
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        // ✅ 감정 분석 테스트 코드
        val analyzer = EmotionAnalyzer(this)
        val testSentences = listOf(
            "정말 행복한 하루야",
            "진짜 짜증나고 화가 나",
            "뭔가 불안하고 걱정돼",
            "배신당한 것 같아",
            "오늘 너무 감사한 일이 있었어",
            "혼란스럽고 당황스러워"
        )
        for (sentence in testSentences) {
            val result = analyzer.analyzeEmotion(sentence)
            Log.d("TestEmotion", "\"$sentence\" → 예측 감정: $result")
        }
        analyzer.close()

        Places.initialize(applicationContext, "AIzaSyDz8UvMNJtUipKK2WRtKjB0IZqmTG1ih3M")
        enableEdgeToEdge()
        setContent {

            HangyulTheme {
                val navController = rememberNavController()

                val currentRoute = navController
                    .currentBackStackEntryAsState().value
                    ?.destination?.route

                val topBarTitle = when {
                    currentRoute == "main" -> "한결이"
                    currentRoute?.startsWith("diary") == true -> "음성 일기"
                    currentRoute?.startsWith("routine") == true -> "일상 관리"
                    currentRoute?.startsWith("brainTraining") == true -> "두뇌 훈련"
                    currentRoute?.startsWith("brain_answer") == true -> "두뇌 훈련"
                    currentRoute?.startsWith("brain_result") == true -> "두뇌 훈련"
                    currentRoute?.startsWith("memory") == true -> "추억 기록"
                    currentRoute?.startsWith("alarm") == true -> "일상 관리"
                    currentRoute?.startsWith("map") == true -> "일상 관리"
                    else -> "한결이"
                }

                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        if (currentRoute != "main") {
                            TopBar(topBarTitle)
                        }
                    },
                    bottomBar = {NaviBar(navController)})
                { innerPadding ->
                    NavGraph(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}