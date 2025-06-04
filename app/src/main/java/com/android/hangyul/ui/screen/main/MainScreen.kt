package com.android.hangyul.ui.screen.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen() {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        FeatureButtonGrid()
    }
}