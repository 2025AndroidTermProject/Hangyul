package com.android.hangyul.ui.screen.memory

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.android.hangyul.viewmodel.Memory
import com.android.hangyul.viewmodel.MemoryViewModel


@Composable
fun MemoryDetailPage(navController: NavController,memoryId: String?, viewModel: MemoryViewModel) {
    val memories by viewModel.memories.collectAsState()
    val memory = memories.find { it.id == memoryId }


    if (memory == null) {
        Text("해당 추억을 찾을 수 없습니다.")
        return
    }

    Log.d("MemoryDetail", "ID: ${memory.id}, Title: ${memory.title}, ImageURL: ${memory.imageUrl}")



    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = memory.title,
            style = TextStyle(
                fontSize = 30.sp,
                lineHeight = 30.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_bold)),
            ))
        Spacer(modifier = Modifier.height(6.dp))

        Text(text = memory.date,
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 40.dp),
            textAlign = TextAlign.End,
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 30.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_semibold)),
                fontWeight = FontWeight(600),
                color = Color(0xFF634F96),
            ))

        Spacer(modifier = Modifier.height(20.dp))

        memory.imageUrl?.let { imageUrl ->
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .width(250.dp)
                    .height(180.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))


        Text(text = memory.content,
            style = TextStyle(
                fontSize = 23.sp,
                lineHeight = 30.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                fontWeight = FontWeight(500),
                color = Color(0xFF000000),
            )
        )

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
