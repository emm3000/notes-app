package com.emm.noteapp.screens.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emm.noteapp.ui.components.CustomTextField
import com.emm.noteapp.ui.theme.NoteAppTheme

@OptIn(ExperimentalTextApi::class)
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
                .padding(top = 25.dp)
                .align(Alignment.CenterHorizontally),
            style = TextStyle(
                brush = Brush.horizontalGradient(listOf(Color.Green, Color.Blue))
            )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = (screenHeight * 0.1f))
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            var text by remember {
                mutableStateOf("")
            }
            CustomTextField(
                value = { text },
                onValueChange = { text = it },
                label = "Username"
            )
            Spacer(modifier = Modifier.height(10.dp))

            CustomTextField(
                value = { text },
                onValueChange = { text = it },
                label = "Password",
                isPasswordType = true
            )

            Spacer(modifier = Modifier.height(30.dp))
            CustomButton("Login") {}
            Spacer(modifier = Modifier.height(14.dp))
            CustomButton("Registro") {}

        }
    }

}

@Composable
private fun CustomButton(
    label: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(45.dp)
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(listOf(Color.Green, Color.Blue)),
                shape = RoundedCornerShape(50),
            ),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
        ),
        elevation = null,
    ) {
        Text(text = label, color = Color.White)
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