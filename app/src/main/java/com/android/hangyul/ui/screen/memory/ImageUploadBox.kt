package com.android.hangyul.ui.screen.memory

import android.content.Intent
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ImageUploadBox(
    imagePaths: List<String>,
    onUploadClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(300.dp)
            .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp))
            .padding(12.dp)
    ) {
        for (path in imagePaths) {
            Text(
                text = path,
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 업로드 버튼
        Button(
            onClick = onUploadClicked,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text("사진 업로드")
        }
    }
}
