package com.android.hangyul.ui.screen.diary

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.android.hangyul.ui.components.NaviBar
import com.android.hangyul.ui.components.TopBar

@Composable
fun DiaryPage() {
    Scaffold (
        topBar = {
            TopBar("음성 일기")
        },
        bottomBar = {
            NaviBar()
        }
    ){  }
}

@Preview
@Composable
fun DiaryPagePreview() {
    DiaryPage()
}
