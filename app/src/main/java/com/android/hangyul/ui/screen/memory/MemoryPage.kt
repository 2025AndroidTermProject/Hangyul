package com.android.hangyul.ui.screen.memory

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.android.hangyul.ui.components.NaviBar
import com.android.hangyul.ui.components.TopBar
import com.android.hangyul.ui.screen.diary.DiaryPage

@Composable
fun MemoryPage() {
    Scaffold (
        topBar = {
            TopBar("추억 기록")
        },
        bottomBar = {
            NaviBar()
        }
    ){  }
}

@Preview
@Composable
fun MemoryPagePreview() {
    MemoryPage()
}