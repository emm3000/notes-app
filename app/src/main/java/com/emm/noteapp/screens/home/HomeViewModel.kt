package com.emm.noteapp.screens.home

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emm.noteapp.api.TaskRepository
import com.emm.noteapp.screens.home.data.Task
import com.emm.noteapp.screens.home.state.HomeState
import com.emm.noteapp.screens.home.state.TaskType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.UUID

class HomeViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState get() = _homeState.asStateFlow()

    private val _pastTaskList = mutableStateListOf<Task>()
    private val _currentTaskList = mutableStateListOf<Task>()
    private val _futureTaskList = mutableStateListOf<Task>()

    val pastTaskList get() = _pastTaskList
    val currentTaskList get() = _currentTaskList
    val futureTaskList get() = _futureTaskList

    init {
        merge(
            repository.loadTaskByType(TaskType.PAST.ordinal),
            repository.loadTaskByType(TaskType.CURRENT.ordinal),
            repository.loadTaskByType(TaskType.FUTURE.ordinal),
        )
            .onEach { pairValue ->
                when (pairValue.first) {
                    TaskType.PAST.ordinal -> handleNewsTask(pairValue.second, _pastTaskList)
                    TaskType.CURRENT.ordinal -> handleNewsTask(pairValue.second, _currentTaskList)
                    TaskType.FUTURE.ordinal -> handleNewsTask(pairValue.second, _futureTaskList)
                }
            }.launchIn(viewModelScope)
    }

    private fun handleNewsTask(newList: List<Task>, currentList: SnapshotStateList<Task>) {
        currentList.clear()
        currentList.addAll(newList)
    }

    fun onTitleChange(title: String) {
        _homeState.value = _homeState.value.copy(
            title = title
        )
    }

    fun onDescriptionChange(description: String) {
        _homeState.value = _homeState.value.copy(
            description = description
        )
    }

    fun onSelectedTypeTaskChange(taskType: TaskType) {
        _homeState.value = _homeState.value.copy(
            type = taskType
        )
    }

    fun onSaveTask() {
        val task = Task(
            id = UUID.randomUUID().toString(),
            title = _homeState.value.title,
            description = _homeState.value.description,
            type = _homeState.value.type.ordinal,
            taskCreateDate = System.currentTimeMillis(),
            taskUpdateDate = System.currentTimeMillis()
        )

        viewModelScope.launch {
            repository.insertTask(task)
            _homeState.value = HomeState()
        }
    }

}