package com.android.hangyul.ui.screen.memory

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
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.hangyul.R
import com.android.hangyul.ui.components.AddBtn
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.style.TextAlign
import com.android.hangyul.viewmodel.Memory


@Composable
fun MemoryDetailPage(navController: NavController) {
    val memory = navController.previousBackStackEntry
        ?.savedStateHandle
        ?.get<Memory>("selectedMemory")


    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = memory?.title ?: "제목 없음")
        Text(text = memory?.date ?: "날짜 없음")
        Text(text = memory?.content ?: "내용 없음")

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 25.dp, bottom = 25.dp),
            horizontalArrangement = Arrangement.End
        ) {
            AddBtn("추억 등록", modifier = Modifier.clickable { navController.navigate("memoryAdd") })
        }
    }
}
