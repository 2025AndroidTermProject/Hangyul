package com.android.hangyul.ui.screen.braintraining

import CircleNumber
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.hangyul.R
import com.android.hangyul.ui.theme.HangyulTheme

@Composable
fun BrainTrainingResultPage(
    correctNumbers: List<Int>,
    userNumbers: List<Int>
) {
    val correctMatched = mutableListOf<Int>()
    val incorrect = mutableListOf<Int>()

    for (i in userNumbers.indices) {
        if (i >= correctNumbers.size) break

        val userNum = userNumbers[i]
        val correctNum = correctNumbers[i]

        if (userNum == correctNum) {
            correctMatched.add(userNum)
        } else {
            incorrect.add(userNum)
        }
    }
    val incorrectPairs = correctNumbers.zip(userNumbers)
        .mapIndexedNotNull { index, (correct, user) ->
            if (correct != user) index to correct else null
        }

    val correctAnswersForWrong = incorrectPairs.map { (_, correct) -> correct }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F0FF))
            .padding(20.dp)
            .padding(top = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (correctMatched.size == correctNumbers.size) "완벽해요! 🎉" else "아쉬워요😅",
            style = TextStyle(
                fontSize = 30.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                fontWeight = FontWeight(600),
                color = Color(0xFF000000)
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "틀린 번호 : [${incorrect.joinToString(",")}]",
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 30.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                fontWeight = FontWeight(600),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "맞는 답 : [${correctAnswersForWrong.joinToString(",")}]"
            ,style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 30.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                fontWeight = FontWeight(600),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(40.dp))

        // 숫자 원 출력 (3개씩, 2개씩 두 줄로)
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // 첫 번째 줄 (3개)
            Row(horizontalArrangement = Arrangement.spacedBy(30.dp)) {
                correctNumbers.take(3).forEachIndexed { index, number ->
                    val isCorrect = index < userNumbers.size && userNumbers[index] == number
                    CircleNumber(
                        number = userNumbers[index],
                        backgroundColor = when {
                            isCorrect -> Color(0xFF928FFF) // 보라 (정답)
                            else -> Color(0xFFD06060) // 빨강 (틀린 위치)
                        }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // 두 번째 줄 (2개)
            Row(horizontalArrangement = Arrangement.spacedBy(30.dp)) {
                correctNumbers.drop(3).take(2).forEachIndexed { index, number ->
                    val actualIndex = index + 3
                    val isCorrect = actualIndex < userNumbers.size && userNumbers[actualIndex] == number
                    CircleNumber(
                        number = userNumbers[actualIndex],
                        backgroundColor = when {
                            isCorrect -> Color(0xFF928FFF) // 보라 (정답)
                            else -> Color(0xFFD06060) // 빨강 (틀린 위치)
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun BrainTrainingResultPagePreview() {
    HangyulTheme {
        BrainTrainingResultPage(
            correctNumbers = listOf(1, 2, 3, 4, 5),
            userNumbers = listOf(1, 2, 3, 4, 5)
        )
    }
}
