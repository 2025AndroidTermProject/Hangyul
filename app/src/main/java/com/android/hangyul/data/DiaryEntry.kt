package com.android.hangyul.data

import java.util.Date

data class DiaryEntry(
    val id: String = "",
    val date: Date = Date(),
    val content: String = "",
    val emotion: String = "",
    val comfortMessage: String = ""
) 