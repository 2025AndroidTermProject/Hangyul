package com.android.hangyul.ui.screen.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.android.hangyul.ui.theme.HangyulTheme

@Composable
fun MainPage(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("메인 페이지") // 예시
    }
}

@Composable
@Preview
fun MainPagePreivew(){
    HangyulTheme {
        val navController = androidx.navigation.compose.rememberNavController()
        MainPage(navController = navController)
    }
}