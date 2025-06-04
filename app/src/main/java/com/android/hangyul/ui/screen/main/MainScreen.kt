package com.android.hangyul.ui.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.hangyul.ui.theme.HangyulTheme

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        EmotionAnalysisCard(
            "오늘은 조금 지친 마음이 느껴졌어요",
            "오늘도 잘 버텼어요. 마음이 괜찮아 질거예요.",
            "5/27(화)",
            onClick = {})
        FeatureButtonGrid()
    }
}
@Composable
@Preview
fun MainScreenPreview(){
    HangyulTheme {
        MainScreen()
    }
}