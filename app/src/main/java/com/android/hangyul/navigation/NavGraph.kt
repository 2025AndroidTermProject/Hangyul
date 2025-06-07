package com.android.hangyul.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalWithComputedDefaultOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.android.hangyul.ui.screen.braintraining.BrainTrainingInitialPage
import com.android.hangyul.ui.screen.braintraining.BrainTrainingAnswerPage
import com.android.hangyul.ui.screen.braintraining.BrainTrainingResultPage
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
import com.android.hangyul.viewmodel.MapViewModel
import com.android.hangyul.viewmodel.MemoryViewModel

private object Routes {
    const val MAIN = "main"
    const val BRAIN_TRAINING = "brainTraining"
    const val BRAIN_ANSWER = "brain_answer"
    const val BRAIN_RESULT = "brain_result"
    const val ROUTINE = "routine"
    const val DIARY = "diary"
    const val MEMORY = "memory"

    const val ALARM_LIST = "alarmList"
    const val ALARM_ADD = "alarmAdd"

    const val MEMORY_DETAIL = "memoryDetail/{memoryId}"
    const val MEMORY_ADD = "memoryAdd"

    const val MAP_LIST = "mapList"
    const val MAP_ADD = "mapAdd"
}

@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    val sharedMapViewModel: MapViewModel = viewModel()
    val sharedMemoryViewModel: MemoryViewModel = viewModel()

    NavHost(navController = navController, startDestination = Routes.MAIN, modifier = modifier) {
        composable(Routes.MAIN) { MainPage(navController) }
        composable(Routes.BRAIN_TRAINING) { BrainTrainingInitialPage(navController) }
        composable(
            route = "${Routes.BRAIN_ANSWER}?correctNumbers={correctNumbers}",
        ) { backStackEntry ->
            val correctNumbersStr = backStackEntry.arguments?.getString("correctNumbers") ?: ""
            println("Answer received numbers: $correctNumbersStr") // 디버깅용 로그
            
            val correctNumbers = correctNumbersStr.split(",")
                .mapNotNull { it.toIntOrNull() }
            println("Parsed numbers: $correctNumbers") // 디버깅용 로그
            
            if (correctNumbers.size == 5) {
                BrainTrainingAnswerPage(navController, correctNumbers)
            } else {
                navController.popBackStack()
            }
        }
        composable(
            route = "${Routes.BRAIN_RESULT}?correct={correct}&user={user}",
        ) { backStackEntry ->
            val correctStr = backStackEntry.arguments?.getString("correct") ?: ""
            val userStr = backStackEntry.arguments?.getString("user") ?: ""
            
            val correctNumbers = correctStr.split(",").mapNotNull { it.toIntOrNull() }
            val userNumbers = userStr.split(",").mapNotNull { it.toIntOrNull() }
            
            if (correctNumbers.size == 5 && userNumbers.size == 5) {
                BrainTrainingResultPage(
                    correctNumbers = correctNumbers,
                    userNumbers = userNumbers
                )
            } else {
                println(correctNumbers)
                println(userNumbers)
                // 잘못된 데이터가 전달된 경우 이전 화면으로 돌아가기
                navController.popBackStack()
            }
        }
        composable(Routes.ROUTINE) { RoutinePage(navController) }
        composable(Routes.DIARY) { DiaryPage(navController) }

        composable(Routes.ALARM_LIST) { AlarmListPage(navController) }
        composable(Routes.ALARM_ADD) {
            AlarmAddPage(viewModel = viewModel()) {
                navController.popBackStack()
            }
        }

        composable(Routes.MEMORY) {
            MemoryPage(navController, viewModel = sharedMemoryViewModel)
        }
        composable(Routes.MEMORY_ADD) {
            MemoryAddPage(viewModel = sharedMemoryViewModel) {
                navController.popBackStack()
            }
        }
        composable("memoryDetail/{memoryId}"){ backStackEntry ->
            val memoryId = backStackEntry.arguments?.getString("memoryId")
            MemoryDetailPage(navController, memoryId, sharedMemoryViewModel)
        }

        composable(Routes.MAP_LIST) {
            MapListPage(navController, sharedMapViewModel)
        }
        composable(Routes.MAP_ADD) {
            MapMarkerAddPage(navController, sharedMapViewModel)
        }
    }
}