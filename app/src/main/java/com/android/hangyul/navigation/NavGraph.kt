package com.android.hangyul.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.hangyul.ui.screen.braintraining.BrainTrainingPage
import com.android.hangyul.ui.screen.diary.DiaryPage
import com.android.hangyul.ui.screen.main.MainPage
import com.android.hangyul.ui.screen.memory.MemoryPage
import com.android.hangyul.ui.screen.routine.AlarmAddPage
import com.android.hangyul.ui.screen.routine.AlarmListPage
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
        composable("alarmAdd") {
            AlarmAddPage(viewModel = viewModel()) {
                navController.popBackStack()
            }
        }
    }
}