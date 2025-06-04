package com.android.hangyul.ui.screen.memory

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.android.hangyul.ui.components.NaviBar
import com.android.hangyul.ui.components.TopBar

@Composable
fun MemoryPage(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("추억 기록 페이지") // 예시
    }
}
