package com.android.hangyul.ui.screen.memory

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.android.hangyul.R
import com.android.hangyul.ui.components.AddBtn
import com.android.hangyul.ui.components.ListBtn
import com.android.hangyul.ui.components.NaviBar
import com.android.hangyul.ui.components.TopBar
import com.android.hangyul.ui.screen.routine.location
import com.android.hangyul.ui.screen.routine.medicine
import com.android.hangyul.viewmodel.MemoryViewModel

@Composable
fun MemoryPage(navController: NavController, viewModel: MemoryViewModel = viewModel()) {
    val memories by viewModel.memories.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchMemories()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            contentPadding = PaddingValues(
                top = 10.dp,
                bottom = 40.dp,
                start = 30.dp,
                end = 30.dp
            )
        ) {
            items(memories) { memory ->
                ListBtn(
                    icon = R.drawable.ic_nav_memory,
                    title = memory.title,
                    date = memory.date,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("memoryDetail/${memory.id}")
                        }
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 25.dp, bottom = 25.dp)
                .align(Alignment.BottomEnd),
            horizontalArrangement = Arrangement.End
        ) {
            AddBtn("추억 등록", modifier = Modifier.clickable { navController.navigate("memoryAdd") })
        }
    }
}

