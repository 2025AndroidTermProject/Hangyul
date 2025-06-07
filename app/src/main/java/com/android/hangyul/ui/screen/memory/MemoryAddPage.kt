package com.android.hangyul.ui.screen.memory

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.hangyul.R
import com.android.hangyul.ui.components.AddInputField
import com.android.hangyul.viewmodel.Memory
import com.android.hangyul.viewmodel.MemoryViewModel

@Composable
fun MemoryAddPage(viewModel: MemoryViewModel, onSave: ()->Unit) {

    var title by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp, vertical = 20.dp),
    ) {
        Text(
            text = "추억을 기록하세요!",
            style = TextStyle(
                fontSize = 23.sp,
                lineHeight = 30.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_bold)),
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("제목") })
        OutlinedTextField(value = date, onValueChange = { date = it }, label = { Text("날짜") })
        OutlinedTextField(value = content, onValueChange = { content = it }, label = { Text("내용") })


        Spacer(modifier = Modifier.height(10.dp))

//        ImageUploadBox(
//            imagePaths = viewModel.images,
//            onUploadClicked = {
//                //TODO: 이미지 선택 로직 연결
//                viewModel.addImage("new_image_123.png")
//            }
//        )

        Spacer(modifier = Modifier.height(10.dp))
        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                val memory = Memory(title = title, date = date, content = content)
                viewModel.addMemory(memory)
                onSave()
            },
            modifier = Modifier
                .width(300.dp)
                .align(Alignment.CenterHorizontally)
        )
        {
            Text("저장")
        }
    }
}
