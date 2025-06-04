package com.android.hangyul.ui.screen.braintraining
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.android.hangyul.ui.components.NaviBar
import com.android.hangyul.ui.components.TopBar

@Composable
fun BrainTrainingPage() {
    Scaffold (
        topBar = {
            TopBar("두뇌 훈련")
        },
        bottomBar = {
            NaviBar()
        }
    ){  }
}

@Preview
@Composable
fun BrainTrainingPagePreview() {
    BrainTrainingPage()
}

