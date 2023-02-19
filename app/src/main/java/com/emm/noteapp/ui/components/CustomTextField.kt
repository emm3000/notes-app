package com.emm.noteapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emm.noteapp.R

@Composable
fun CustomTextField(
    isPasswordType: Boolean = false,
    label: String = "",
    value: () -> String,
    onValueChange: (String) -> Unit
) {

    val (showPassword, setShowPassword) = remember {
        mutableStateOf(false)
    }

    val icon = if (showPassword) {
        painterResource(id = R.drawable.ic_eye_show)
    } else painterResource(id = R.drawable.ic_eye_no_show)

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = Color.Black.copy(alpha = .8f),
            modifier = Modifier.padding(start = 15.dp, bottom = 5.dp),
            fontSize = 14.sp
        )
        BasicTextField(
            value = value.invoke(),
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
                .border(
                    width = 2.dp,
                    brush = Brush.horizontalGradient(listOf(Color.Green, Color.Blue)),
                    shape = RoundedCornerShape(50)
                ),
            singleLine = true,
            visualTransformation = if (showPassword && isPasswordType) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        if (value.invoke().isEmpty()) {
                            Text(
                                text = label,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.LightGray
                            )
                        }
                        innerTextField()
                    }
                    if (isPasswordType) {
                        Spacer(modifier = Modifier.width(5.dp))
                        IconToggleButton(
                            checked = showPassword,
                            onCheckedChange = setShowPassword
                        ) {
                            Icon(
                                painter = icon, contentDescription = "", tint = Color.LightGray
                            )
                        }
                    }
                }
            })
    }
}

fun Modifier.log(color: Color = Color.Red) = then(Modifier.border(width = 1.dp, color = color))