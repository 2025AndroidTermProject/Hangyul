package com.android.hangyul.ui.screen.routine

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.android.hangyul.ui.components.NaviBar
import com.android.hangyul.ui.components.TopBar
import com.android.hangyul.ui.screen.diary.DiaryPage

@Composable
fun RoutinePage() {
    Scaffold (
        topBar = {
            TopBar("일상 관리")
        },
        bottomBar = {
            NaviBar()
        }
    ){  }
}

@Preview
@Composable
fun RoutinePagePreview() {
    RoutinePage()
}