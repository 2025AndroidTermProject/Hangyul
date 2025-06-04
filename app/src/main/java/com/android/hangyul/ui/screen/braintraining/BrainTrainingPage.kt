package com.android.hangyul.ui.screen.braintraining
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
fun BrainTrainingPage(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("두뇌훈련 페이지")
    }
}
