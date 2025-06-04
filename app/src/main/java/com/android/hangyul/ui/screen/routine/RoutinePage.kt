package com.android.hangyul.ui.screen.routine

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController


@Composable
fun RoutinePage(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("일상 관리 페이지") // 예시
    }
}
