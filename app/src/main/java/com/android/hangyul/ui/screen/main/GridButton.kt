package com.android.hangyul.ui.screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.hangyul.R
import com.android.hangyul.ui.theme.HangyulTheme
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

data class FeatureButtonData(
    val imageRes: Int,
    val title: String,
    val description: String
)
@Composable
fun FeatureButtonGrid(navController: NavController) {
    val buttons = listOf(
        FeatureButtonData(R.drawable.ic_main_diary, "음성 일기", "하루를 기록해보세요."),
        FeatureButtonData(R.drawable.ic_main_routine, "일상 관리", "당신의 일상에 도움을"),
        FeatureButtonData(R.drawable.ic_main_braintraining, "두뇌 훈련", "뇌를 활성화 시키자"),
        FeatureButtonData(R.drawable.ic_main_memory, "추억 기록", "소중한 순간을 기억해요.")
    )

    BoxWithConstraints {
        val buttonSize = (maxWidth - 16.dp * 3) / 2  // 여백 고려한 셀 너비

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            userScrollEnabled = false
        ) {
            items(buttons) { button ->
                FeatureButton(
                    imageRes = button.imageRes,
                    buttonTitle = button.title,
                    buttonText = button.description,
                    buttonSize = buttonSize,
                    onClick = {
                        when (button.title) {
                            "음성 일기" -> navController.navigate("diary")
                            "일상 관리" -> navController.navigate("routine")
                            "두뇌 훈련" -> navController.navigate("brainTraining")
                            "추억 기록" -> navController.navigate("memory")
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeatureButtonGridPreview() {
    HangyulTheme {
        FeatureButtonGrid(navController = rememberNavController())
    }
}
