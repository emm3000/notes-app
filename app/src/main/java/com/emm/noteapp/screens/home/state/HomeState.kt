package com.emm.noteapp.screens.home.state

import com.emm.noteapp.screens.home.TaskType

data class HomeState(
    val title: String = "",
    val description: String = "",
    val type: TaskType = TaskType.CURRENT,
    val saveDate: Long = 0L,
    val updateDate: Long = 0L
)