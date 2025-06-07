package com.android.hangyul.ui.screen.braintraining

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

@Composable
fun BrainTrainingAnswerPage(
    navController: NavController,
    correctNumbers: List<Int>
) {
    val inputs = remember { List(5) { mutableStateOf("") } }
    val focusManager = LocalFocusManager.current

    // 모든 입력이 끝났는지 확인 후 이동
    LaunchedEffect(inputs.map { it.value }) {
        if (inputs.all { it.value.isNotBlank() }) {
            val userNumbers = inputs.map { it.value.toIntOrNull() ?: 0 }
            delay(300)
            navController.navigate(
                "brain_result?correct=${correctNumbers.joinToString(",")}&user=${userNumbers.joinToString(",")}"
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F0FF))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(40.dp))

        Text(
            text = "꿈에서 본 로또번호를\n입력하세요!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 30.sp,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(24.dp))

        val colors = listOf(
            Color(0xFF333333), Color(0xFFE2B178),
            Color(0xFFE2B178), Color(0xFF928FFF), Color(0xFF928FFF)
        )

        // 위에 3개
        Row(
            horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterHorizontally),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically

        ) {
            for (i in 0 until 3) {
                CircleInputNumber(
                    value = inputs[i].value,
                    onValueChange = { inputs[i].value = it },
//                    modifier = Modifier.weight(1f),
                    backgroundColor = colors[i]
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        // 아래 2개
        Row(
            horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterHorizontally),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 3 until 5) {
                CircleInputNumber(
                    value = inputs[i].value,
                    onValueChange = { inputs[i].value = it },
//                    modifier = Modifier.weight(1f),
                    backgroundColor = colors[i]
                )
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun BrainTrainingAnswerPagePreview() {
    val navController = rememberNavController()
    val dummyCorrectNumbers = listOf(12, 23, 34, 45, 7)

    BrainTrainingAnswerPage(
        navController = navController,
        correctNumbers = dummyCorrectNumbers
    )
}