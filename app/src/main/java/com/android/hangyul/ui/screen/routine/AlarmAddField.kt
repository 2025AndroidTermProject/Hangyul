package com.android.hangyul.ui.screen.routine

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.draw.shadow
import com.android.hangyul.ui.theme.Gray80

@Composable
fun AlarmAddField(text: String) {
    var inputText by remember { mutableStateOf("") }

    TextField(
        value = inputText,
        onValueChange = { inputText = it },
        placeholder = {
            Text(
                text = text,
                color = Gray80
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(12.dp),
                spotColor = Color(0x33000000),     // 오른쪽 아래쪽 방향 그림자 효과
                ambientColor = Color(0x33000000)
            ),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            errorContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        )
    )
}



@Preview
@Composable
fun AlarmAddFieldPreview() {
    AlarmAddField("알람이셔")
}