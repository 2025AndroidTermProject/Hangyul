package com.android.hangyul.ui.screen.braintraining

data class BrainTrainingState(
    val numbersToMemorize: List<Int> = listOf(),
    val userInput: List<Int> = listOf(),
    val correctAnswers: List<Int> = listOf(),
    val wrongAnswers: List<Int> = listOf(),
    val trialCount: Int = 0,
    val successCount: Int = 0
)