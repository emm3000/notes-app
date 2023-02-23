package com.emm.noteapp.screens.login.state

sealed class LoginState {

    object Loading : LoginState()

    object Init : LoginState()

    object LoginSuccess : LoginState()

    class LoginFailed(
        val errorMessage: String
    ) : LoginState()

}
