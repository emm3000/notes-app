package com.emm.noteapp.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emm.noteapp.screens.login.state.LoginForm
import com.emm.noteapp.screens.login.state.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _formState: MutableStateFlow<LoginForm> = MutableStateFlow(LoginForm())
    val formState get() = _formState.asStateFlow()

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Init)
    val loginState get() = _loginState.asStateFlow()

    fun login() {
        _loginState.value = LoginState.Loading

        viewModelScope.launch {
            _loginState.value = LoginState.LoginSuccess
        }
    }

    fun onPasswordChange(password: String) {
        _formState.value = _formState.value.copy(
            password = password
        )
    }

    fun onUsernameChange(username: String) {
        _formState.value = _formState.value.copy(
            username = username
        )
    }

    fun restartState() {
        _loginState.update { LoginState.Init }
    }

}