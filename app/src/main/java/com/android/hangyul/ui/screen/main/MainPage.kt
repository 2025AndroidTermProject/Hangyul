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
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // 상단 프로필 및 앱 이름
            TopSectionCard()

            Spacer(modifier = Modifier.height(30.dp))

            // 감정 분석 카드
            EmotionAnalysisCard("오늘은 조금 지친 마음이 느껴졌어요",
                "오늘도 잘 버텼어요. 마음이 괜찮아 질거예요.",
                "5/27(화)",
                onClick = {},
                modifier = Modifier)

            Spacer(modifier = Modifier.height(40.dp))

            // 4개 버튼 그리드
            FeatureButtonGrid()

            Spacer(modifier = Modifier.weight(1f))
        }

        // 하단 네비게이션 바
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            NaviBar(navController = rememberNavController())
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