package com.android.hangyul.ui.screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.hangyul.R
import com.android.hangyul.ui.theme.HangyulTheme

@Composable
fun FeatureButtonGrid() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FeatureButton(
                imageRes = R.drawable.ic_main_diary,
                buttonTitle = "음성 일기",
                buttonText = "하루를 기록해보세요.",
                onClick = { /* TODO */ }
            )

            FeatureButton(
                imageRes = R.drawable.ic_main_routine,
                buttonTitle = "일상 관리",
                buttonText = "당신의 일상에 도움을",
                onClick = { /* TODO */ }
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            FeatureButton(
                imageRes = R.drawable.ic_main_braintraining,
                buttonTitle = "두뇌 훈련",
                buttonText = "뇌를 활성화 시키자",
                onClick = { /* TODO */ }
            )

            FeatureButton(
                imageRes = R.drawable.ic_main_memory,
                buttonTitle = "추억 기록",
                buttonText = "소중한 순간을 기억해요.",
                onClick = { /* TODO */ }
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun FeatureButtonGridPreview() {
    HangyulTheme {
        FeatureButtonGrid()
    }
}