package com.emm.noteapp.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emm.noteapp.ui.components.CustomTextField
import com.emm.noteapp.ui.theme.NoteAppTheme

@Composable
fun LoginScreen() {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Note App",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 20.dp)
                .align(Alignment.CenterHorizontally)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = (screenHeight * 0.1f))
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            CustomTextField(
                value = { "" },
                onValueChange = {},
                label = "Username"
            )
            Spacer(modifier = Modifier.height(10.dp))

            CustomTextField(
                value = { "" },
                onValueChange = {},
                label = "Password",
                isPasswordType = true
            )
        }
    }

}

@Preview(
    showBackground = true,
)
@Composable
fun Preview() {
    NoteAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            LoginScreen()
        }
    }
}