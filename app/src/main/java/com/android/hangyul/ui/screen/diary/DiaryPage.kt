package com.android.hangyul.ui.screen.diary

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.hangyul.ui.components.NaviBar
import com.android.hangyul.ui.components.TopBar
import com.android.hangyul.ui.theme.HangyulTheme


@Composable
fun DiaryPage(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("음성 일기 페이지") // 예시
    }
}
@Composable
@Preview
fun DiaryPagePreview(){
    HangyulTheme {
        DiaryPage(navController = rememberNavController())
    }
}