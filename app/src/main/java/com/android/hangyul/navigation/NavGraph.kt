package com.android.hangyul.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.hangyul.ui.screen.braintraining.BrainTrainingPage
import com.android.hangyul.ui.screen.diary.DiaryPage
import com.android.hangyul.ui.screen.main.MainPage
import com.android.hangyul.ui.screen.memory.MemoryPage
import com.android.hangyul.ui.screen.routine.AlarmListPage
import com.android.hangyul.ui.screen.routine.RoutinePage


@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = "main", modifier = modifier) {
        composable("main") {MainPage(navController)}
        composable("brainTraining") { BrainTrainingPage(navController)}
        composable("routine") { RoutinePage(navController) }
        composable("diary") { DiaryPage(navController) }
        composable("memory") { MemoryPage(navController) }

    }
}