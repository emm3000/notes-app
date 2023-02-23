package com.emm.noteapp.screens.home

import androidx.lifecycle.ViewModel
import com.emm.noteapp.screens.home.state.HomeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel(
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState get() = _homeState.asStateFlow()

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
        
    }
}