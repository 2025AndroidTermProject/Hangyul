package com.android.hangyul

import android.os.Bundle
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
import com.android.hangyul.ui.theme.HangyulTheme
import com.android.hangyul.ui.components.NaviBar
import com.android.hangyul.ui.components.TopBar
import com.google.android.libraries.places.api.Places

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Places.initialize(applicationContext, "AIzaSyDz8UvMNJtUipKK2WRtKjB0IZqmTG1ih3M")
        enableEdgeToEdge()
        setContent {
            HangyulTheme {
                val navController = rememberNavController()

                val currentRoute = navController
                    .currentBackStackEntryAsState().value
                    ?.destination?.route

                val topBarTitle = when (currentRoute) {
                    "main" -> "한결이"
                    "diary" -> "음성 일기"
                    "routine" -> "일상 관리"
                    "brainTraining" -> "두뇌 훈련"
                    "memory" -> "추억 기록"
                    "alarmList" -> "일상 관리"
                    "alarmAdd" -> "일상 관리"
                    "memoryDetail/{memoryId}" -> "추억 기록"
                    "memoryAdd" -> "추억 기록"
                    "mapList" -> "일상 관리"
                    "mapAdd" -> "일상 관리"
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