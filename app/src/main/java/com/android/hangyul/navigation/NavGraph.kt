package com.android.hangyul.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalWithComputedDefaultOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.hangyul.ui.screen.braintraining.BrainTrainingPage
import com.android.hangyul.ui.screen.diary.DiaryPage
import com.android.hangyul.ui.screen.main.MainPage
import com.android.hangyul.ui.screen.memory.MemoryAddPage
import com.android.hangyul.ui.screen.memory.MemoryDetailPage
import com.android.hangyul.ui.screen.memory.MemoryPage
import com.android.hangyul.ui.screen.routine.AlarmAddPage
import com.android.hangyul.ui.screen.routine.AlarmListPage
import com.android.hangyul.ui.screen.routine.MapListPage
import com.android.hangyul.ui.screen.routine.MapMarkerAddPage
import com.android.hangyul.ui.screen.routine.RoutinePage
import com.android.hangyul.viewmodel.AlarmViewModel

private object Routes {
    const val MAIN = "main"
    const val BRAIN_TRAINING = "brainTraining"
    const val ROUTINE = "routine"
    const val DIARY = "diary"
    const val MEMORY = "memory"

    const val ALARM_LIST = "alarmList"
    const val ALARM_ADD = "alarmAdd"

    const val MEMORY_DETAIL = "memoryDetail"
    const val MEMORY_ADD = "memoryAdd"

    const val MAP_LIST = "mapList"
    const val MAP_ADD = "mapAdd"
}

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = Routes.MAIN, modifier = modifier) {
        composable(Routes.MAIN) { MainPage(navController) }
        composable(Routes.BRAIN_TRAINING) { BrainTrainingPage(navController) }
        composable(Routes.ROUTINE) { RoutinePage(navController) }
        composable(Routes.DIARY) { DiaryPage(navController) }
        composable(Routes.MEMORY) { MemoryPage(navController) }

        composable(Routes.ALARM_LIST) { AlarmListPage(navController) }
        composable(Routes.ALARM_ADD) {
            AlarmAddPage(viewModel = viewModel()) {
                navController.popBackStack()
            }
        }

        composable(Routes.MEMORY_DETAIL) { MemoryDetailPage(navController)}
        composable(Routes.MEMORY_ADD) { MemoryAddPage(viewModel = viewModel()) {navController.popBackStack() }}

        composable(Routes.MAP_LIST) { MapListPage(navController) }
        composable(Routes.MAP_ADD) { MapMarkerAddPage()}
    }
}