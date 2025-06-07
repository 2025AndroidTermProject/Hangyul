package com.android.hangyul.ui.screen.braintraining

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.hangyul.R
import kotlinx.coroutines.delay

@Composable
fun BrainTrainingAnswerPage(
    navController: NavController,
    correctNumbers: List<Int>
) {
    val inputs = remember { List(5) { mutableStateOf("") } }
    val focusManager = LocalFocusManager.current
    var isAllFilled by remember { mutableStateOf(false) }

    // 모든 입력이 끝났는지 확인
    LaunchedEffect(inputs.map { it.value }) {
        isAllFilled = inputs.all { it.value.isNotBlank() }
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
            text = "꿈에서 본 로또번호를",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = TextStyle(
                fontSize = 30.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                fontWeight = FontWeight(600),
                color = Color(0xFF000000),
            )
        )
        Text(
            text = "입력하세요!",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = TextStyle(
                fontSize = 30.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                fontWeight = FontWeight(600),
                color = Color(0xFF000000),
            )
        )

        Spacer(Modifier.height(32.dp))

        val colors = listOf(
            Color(0xFF333333), Color(0xFFE2B178),
            Color(0xFFE2B178), Color(0xFF928FFF), Color(0xFF928FFF)
        )

        // 위에 3개
        Row(
            horizontalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterHorizontally),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 0 until 3) {
                CircleInputNumber(
                    value = inputs[i].value,
                    onValueChange = { inputs[i].value = it },
                    backgroundColor = colors[i]
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        // 아래 2개
        Row(
            horizontalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterHorizontally),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            for (i in 3 until 5) {
                CircleInputNumber(
                    value = inputs[i].value,
                    onValueChange = { inputs[i].value = it },
                    backgroundColor = colors[i]
                )
            }
        }

        Spacer(Modifier.height(40.dp))

        // 결과 확인 버튼
        Button(
            onClick = {
                if (isAllFilled) {
                    try {
                        val userNumbers = inputs.map { it.value.toIntOrNull() ?: 0 }
                        val route = "brain_result?correct=${correctNumbers.joinToString(",")}&user=${userNumbers.joinToString(",")}"
                        navController.navigate(route) {
                            popUpTo("brain_answer") { inclusive = true }
                            launchSingleTop = true
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            },
            enabled = isAllFilled,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "결과 확인",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                    fontWeight = FontWeight(600),
                    color = Color.White
                )
            )
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