package com.emm.noteapp.api

import com.emm.noteapp.screens.home.data.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    suspend fun insertTask(task: Task)

    fun loadTaskByType(type: Int): Flow<Pair<Int, List<Task>>>

}