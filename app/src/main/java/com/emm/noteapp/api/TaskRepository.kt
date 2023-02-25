package com.emm.noteapp.api

import com.emm.noteapp.screens.home.data.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    fun insertTask(task: Task): Flow<String?>

    fun loadTaskByType(type: Int): Flow<Pair<Int, List<Task>>>

    fun updateTask(task: Task): Flow<Int>

}