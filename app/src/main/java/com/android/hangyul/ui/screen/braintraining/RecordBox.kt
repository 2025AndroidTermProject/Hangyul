package com.android.hangyul.ui.screen.braintraining

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.android.hangyul.ui.theme.HangyulTheme

@Composable
fun RecordBox(date:String){

}

@Composable
@Preview
fun RecordBoxPreview(){
    HangyulTheme {
        RecordBox("2025년 5월 27일 (화)")
    }
}