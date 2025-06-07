package com.android.hangyul.ui.screen.braintraining
import CircleNumber
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.hangyul.R
import kotlinx.coroutines.delay

@Composable
fun BrainTrainingInitialPage(navController: NavController) {
    // 무작위 1~45 중 5개 선택
    val numbers = remember {
        (1..45).shuffled().take(5)
    }

    // 타이머: 30초
    var remainingSeconds by remember { mutableStateOf(5) }

    // 1초마다 감소
    LaunchedEffect(Unit) {
        while (remainingSeconds > 0) {
            delay(1000)
            remainingSeconds--
        }
        // 시간 종료 시 이동
        val numbersStr = numbers.joinToString(",")
        println("Initial numbers: $numbersStr") // 디버깅용 로그
        navController.navigate("brain_answer?correctNumbers=$numbersStr") {
            popUpTo("brainTraining") { inclusive = true }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F0FF))
            .padding(horizontal = 20.dp, vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "번호를 기억하세요!",
            style = TextStyle(
                fontSize = 30.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                fontWeight = FontWeight(600),
                color = Color(0xFF000000),
            )
        )
        Spacer(modifier = Modifier.height(26.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "⏳",
                style = TextStyle(
                    fontSize = 26.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF000000),
                )
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "${remainingSeconds}s", fontSize = 25.sp)
        }
        Spacer(modifier = Modifier.height(34.dp))

        val colors = listOf(
                Color(0xFF333333), // 검정
        Color(0xFFE2B178), // 주황
        Color(0xFFE2B178),
        Color(0xFF928FFF), // 보라
        Color(0xFF928FFF)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // 윗줄 3개
            Row(horizontalArrangement = Arrangement.spacedBy(30.dp)) {
                for (i in 0 until 3) {
                    CircleNumber(
                        number = numbers[i],
                        backgroundColor = colors.getOrElse(i) { Color.LightGray }
                    )
                }
            }
            // 아랫줄 2개
            Row(horizontalArrangement = Arrangement.spacedBy(30.dp)) {
                for (i in 3 until 5) {
                    CircleNumber(
                        number = numbers[i],
                        backgroundColor = colors.getOrElse(i) { Color.LightGray }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BrainTrainingInitialPagePreview() {
    val navController = rememberNavController()
    BrainTrainingInitialPage(navController = navController)
}
