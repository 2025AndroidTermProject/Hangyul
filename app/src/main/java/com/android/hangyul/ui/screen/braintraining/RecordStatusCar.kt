package com.android.hangyul.ui.screen.braintraining

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.hangyul.R
import com.android.hangyul.ui.theme.HangyulTheme

enum class RecordingState {
    Idle,
    Recording,
    Finished
}

@Composable
fun RecordingStatusCard(
    date: String,
    state: RecordingState,
    durationText: String = "",
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFF7800FF).copy(alpha = 0.15f),
                        Color(0xFF0045B5).copy(alpha=0.4f)),
                    startY = 0f, endY = 1000f
                ),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(20.dp)
    ) {
        // 날짜 텍스트
        Text(
            text = date,
            fontSize = 23.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_bold)),
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(20.dp))

        // 상태에 따른 버튼
        Box(
            modifier = Modifier
                .shadow(10.dp, shape = RoundedCornerShape(30.dp))
                .background(Color(0xFFF5F5F5), RoundedCornerShape(30.dp))
                .clickable { onClick() }
                .padding(horizontal = 32.dp, vertical = 14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                when (state) {
                    RecordingState.Idle -> {
                        IconWithText("녹음시작", color = Color(0xFFD32F2F))
                    }

                    RecordingState.Recording -> {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // 빨간점 + 타이머
                            IconWithText(durationText, color = Color(0xFFD32F2F))
                            Spacer(modifier = Modifier.width(12.dp))
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color.Black)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "녹음중지",
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    lineHeight = 30.sp,
                                    fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                                    fontWeight = FontWeight(600),
                                    color = Color(0xFF000000)
                                )
                            )
                        }
                    }

                    RecordingState.Finished -> {
                        Text(
                            text = "녹음완료",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.pretendard_bold)),
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun IconWithText(text: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Canvas(modifier = Modifier.size(16.dp)) {
            drawCircle(color = color)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = TextStyle(
                fontSize = 20.sp,
                lineHeight = 30.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                fontWeight = FontWeight(600),
                color = Color(0xFF000000),

                )
        )
    }
}

@Composable
@Preview(showBackground = true)
fun RecordBoxPreview(){
    HangyulTheme {
        val state = remember { mutableStateOf(RecordingState.Finished) }

        RecordingStatusCard(
            date = "2025년 5월 27일 (화)",
            state = state.value,
            durationText = "01:23",
            onClick = {
                state.value = when (state.value) {
                    RecordingState.Idle -> RecordingState.Recording
                    RecordingState.Recording -> RecordingState.Finished
                    RecordingState.Finished -> RecordingState.Idle
                }
            }
        )
    }
}