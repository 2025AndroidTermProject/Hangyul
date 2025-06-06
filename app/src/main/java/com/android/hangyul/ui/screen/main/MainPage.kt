package com.android.hangyul.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.hangyul.ui.components.NaviBar
import com.android.hangyul.ui.theme.HangyulTheme

@Composable
fun MainPage(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // 상단 보라색 카드
            TopSectionCard()
            Spacer(modifier = Modifier.height(10.dp))

            // 4개 버튼 그리드
            FeatureButtonGrid()
//            Spacer(modifier = Modifier.weight(1f))
        }


    }
}

@Composable
@Preview(showBackground = true)
fun MainPagePreivew(){
    HangyulTheme {
        MainPage(navController = rememberNavController())
    }
}