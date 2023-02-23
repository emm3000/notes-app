package com.emm.noteapp.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.emm.noteapp.navigation.routes.Route
import com.emm.noteapp.screens.login.state.LoginForm
import com.emm.noteapp.screens.login.state.LoginState
import com.emm.noteapp.ui.components.CustomButton
import com.emm.noteapp.ui.components.CustomTextField
import com.emm.noteapp.ui.theme.ChakraBackgroundColor
import com.emm.noteapp.ui.theme.NoteAppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun UseEffect(
    vararg keys: Any?,
    effect: DisposableEffectScope.() -> Unit
) {
    DisposableEffect(*keys) {
        effect()
        onDispose { }
    }
}

@Composable
fun LoginScreen(
    navController: NavController,
    vm: LoginViewModel = koinViewModel()
) {

    val loginState: State<LoginState> = vm.loginState.collectAsState()
    val formState: State<LoginForm> = vm.formState.collectAsState()

    LoginScreen(
        loginState = loginState.value,
        formState = formState.value,
        onClickLogin = vm::login,
        onUsernameChange = vm::onUsernameChange,
        onPasswordChange = vm::onPasswordChange,
        onLoginSuccess = { navController.navigate(Route.HomeRoute.route) },
        onFinishLogin = vm::restartState
    )

}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalTextApi::class)
@Composable
private fun LoginScreen(
    loginState: LoginState,
    formState: LoginForm,
    onClickLogin: () -> Unit,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginSuccess: () -> Unit,
    onFinishLogin: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val keyboardController = LocalSoftwareKeyboardController.current

    UseEffect(loginState) {
        if (loginState is LoginState.Loading) {
            keyboardController?.hide()
        }
        if (loginState is LoginState.LoginSuccess) {
            onLoginSuccess()
            onFinishLogin()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ChakraBackgroundColor)
            .padding(top = 40.dp)
    ) {
        Text(
            text = "Note App",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            style = TextStyle(
                brush = Brush.horizontalGradient(listOf(Color.Green, Color.Blue))
            )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            CustomTextField(
                value = formState.username,
                onValueChange = onUsernameChange,
                label = "Username"
            )
            CustomTextField(
                value = formState.password,
                onValueChange = onPasswordChange,
                label = "Password",
                isPasswordType = true
            )
            Spacer(modifier = Modifier.height(1.dp))
            CustomButton(
                label = "Login",
                onClick = onClickLogin
            )
            CustomButton("Registro") {}
            Spacer(modifier = Modifier.height(14.dp))
            if (loginState is LoginState.Loading) {
                CircularProgressIndicator()
            }
        }
    }
}



@Preview(
    showBackground = true,
)
@Composable
fun Preview() {
    NoteAppTheme {
        LoginScreen(
            loginState = LoginState.Init,
            formState = LoginForm(),
            onClickLogin = {  },
            onUsernameChange = {},
            onPasswordChange = {},
            onLoginSuccess = {}
        ) {
            
        }
    }
}