package com.android.hangyul.ui.screen.memory

import android.content.Intent
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.hangyul.R
import com.android.hangyul.ui.components.AddInputField
import com.android.hangyul.viewmodel.Memory
import com.android.hangyul.viewmodel.MemoryViewModel
import android.util.Log
import java.io.File
import java.io.FileOutputStream

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
            try {
                // 이미지를 앱의 내부 저장소에 복사
                val inputStream = context.contentResolver.openInputStream(it)
                val fileName = "memory_image_${System.currentTimeMillis()}.jpg"
                val file = File(context.filesDir, fileName)
                
                inputStream?.use { stream ->
                    FileOutputStream(file).use { output ->
                        stream.copyTo(output)
                    }
                }
                
                Log.d("MemoryAdd", "Image saved to: ${file.absolutePath}")
                viewModel.addImage(file.absolutePath)
            } catch (e: Exception) {
                Log.e("MemoryAdd", "Error saving image", e)
            }
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

        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("제목") },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(12.dp)
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
        Spacer(modifier = Modifier.height(15.dp))
        TextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("날짜") },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(12.dp)
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
        Spacer(modifier = Modifier.height(15.dp))
        TextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("내용") },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(12.dp)
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

private fun getRealPathFromUri(context: android.content.Context, uri: Uri): String? {
    try {
        if (DocumentsContract.isDocumentUri(context, uri)) {
            val docId = DocumentsContract.getDocumentId(uri)
            if ("com.android.providers.media.documents" == uri.authority) {
                val id = docId.split(":")[1]
                val selection = "_id=?"
                return getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, arrayOf(id))
            }
        } else if ("content" == uri.scheme) {
            return getDataColumn(context, uri, null, null)
        } else if ("file" == uri.scheme) {
            return uri.path
        }
    } catch (e: Exception) {
        Log.e("MemoryAdd", "Error getting real path from URI", e)
    }
    return null
}

private fun getDataColumn(context: android.content.Context, uri: Uri, selection: String?, selectionArgs: Array<String>?): String? {
    var cursor: android.database.Cursor? = null
    val column = "_data"
    val projection = arrayOf(column)
    try {
        cursor = context.contentResolver.query(uri, projection, selection, selectionArgs, null)
        if (cursor?.moveToFirst() == true) {
            val columnIndex = cursor.getColumnIndexOrThrow(column)
            return cursor.getString(columnIndex)
        }
    } catch (e: Exception) {
        Log.e("MemoryAdd", "Error getting data column", e)
    } finally {
        cursor?.close()
    }
    return null
}
