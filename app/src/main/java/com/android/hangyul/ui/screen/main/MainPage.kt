package com.android.hangyul.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.android.hangyul.ui.components.NaviBar
import com.android.hangyul.ui.theme.HangyulTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.hangyul.viewmodel.DiaryViewModel
import java.time.LocalDate
import java.time.ZoneId
import java.text.SimpleDateFormat
import java.util.Locale
import androidx.compose.ui.platform.LocalContext
import android.app.Application

@Composable
fun MainPage(
    navController: NavController,
    viewModel: DiaryViewModel = viewModel(factory = androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.getInstance(LocalContext.current.applicationContext as Application))
) {
    val entries by viewModel.allEntries.collectAsState(initial = emptyList())
    val today = LocalDate.now()
    
    // 오늘의 일기 찾기
    val todayEntry = entries.firstOrNull { entry ->
        val entryDate = entry.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        entryDate == today
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // 상단 보라색 카드
            TopSectionCard(
                todayEntry = todayEntry,
                formattedDate = SimpleDateFormat("M/d(E)", Locale.KOREAN).format(todayEntry?.date ?: java.util.Date())
            )
            Spacer(modifier = Modifier.height(10.dp))

            // 4개 버튼 그리드
            FeatureButtonGrid(navController = navController)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MainPagePreivew(){
    HangyulTheme {
        MainPage(navController = rememberNavController())
    }
}