package com.emm.noteapp.screens.home.state

enum class TaskType {
    PAST,
    CURRENT,
    FUTURE;

    companion object {
        infix fun from(ordinal: Int): TaskType = TaskType.values()
            .filterIndexed { index, _ -> index == ordinal }
            .firstOrNull() ?: CURRENT
    }
}