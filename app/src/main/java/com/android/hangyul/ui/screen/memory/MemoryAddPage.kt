package com.android.hangyul.ui.screen.memory

import android.content.Intent
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.hangyul.R
import com.android.hangyul.viewmodel.Memory
import com.android.hangyul.viewmodel.MemoryViewModel

@Composable
fun MemoryAddPage(viewModel: MemoryViewModel, onSave: ()->Unit) {

    var title by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    val context = LocalContext.current
    val imageUri by remember { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val uri = result.data?.data
        uri?.let {
            viewModel.addImage(it.toString())
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(55.dp),
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

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("제목") },
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("날짜") },
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("내용") },
            modifier = Modifier.align(Alignment.CenterHorizontally),
        )


        Spacer(modifier = Modifier.height(10.dp))

        ImageUploadBox(
            imagePaths = listOfNotNull(viewModel.imageUrl),
            onUploadClicked = {
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                launcher.launch(intent)
            }
        )

        Spacer(modifier = Modifier.height(10.dp))
        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                val memory = Memory(
                    title = title,
                    date = date,
                    content = content,
                    imageUrl = viewModel.imageUrl  // 이걸로 저장
                )
                viewModel.addMemory(memory)
                viewModel.clearInputs()
                onSave()
            },
            modifier = Modifier
                .fillMaxWidth(),
        )
        {
            Text("저장")
        }
    }
}
