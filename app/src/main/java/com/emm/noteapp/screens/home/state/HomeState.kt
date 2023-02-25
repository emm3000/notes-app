package com.emm.noteapp.screens.home.state

data class HomeState(
    val id: String? = null,
    val title: String = "",
    val description: String = "",
    val type: TaskType = TaskType.CURRENT,
    val saveDate: Long = 0L,
    val updateDate: Long = 0L,
    val isEditing: Boolean = false,
    val showDialog: Boolean = false,
    val buttonText: String = "Add Task"
)