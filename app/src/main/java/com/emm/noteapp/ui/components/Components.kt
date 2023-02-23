package com.emm.noteapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emm.noteapp.R
import com.emm.noteapp.screens.home.TaskType
import com.emm.noteapp.ui.theme.ChakraBackgroundColor
import com.emm.noteapp.ui.theme.ChakraColorGray
import com.emm.noteapp.ui.theme.ChakraColorLightBlue
import com.emm.noteapp.ui.theme.ChakraColorText
import com.emm.noteapp.ui.theme.NoteAppTheme

@Composable
fun CustomTextField(
    isPasswordType: Boolean = false,
    label: String = "",
    value: String,
    onValueChange: (String) -> Unit
) {

    val (showPassword, setShowPassword) = remember {
        mutableStateOf(false)
    }

    val (isFocused, setIsFocused) = remember {
        mutableStateOf(false)
    }

    val icon = if (showPassword) {
        painterResource(id = R.drawable.ic_eye_show)
    } else painterResource(id = R.drawable.ic_eye_no_show)

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = ChakraColorText,
            modifier = Modifier.padding(start = 15.dp, bottom = 5.dp),
            fontSize = 14.sp
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
                .onFocusChanged { focusState: FocusState ->
                    setIsFocused(focusState.isFocused)
                }.run {
                    if (isFocused) {
                        border(
                            width = 1.dp,
                            shape = RoundedCornerShape(50),
                            color = ChakraColorLightBlue
                        )
                    } else {
                        border(
                            width = 1.dp,
                            shape = RoundedCornerShape(50),
                            color = ChakraColorGray
                        )
                    }
                },
            singleLine = true,
            visualTransformation = if (showPassword && isPasswordType) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            textStyle = TextStyle(color = ChakraColorText),
            cursorBrush = SolidColor(ChakraColorText),
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
                        if (value.isEmpty()) {
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
                                painter = icon, contentDescription = "", tint = ChakraColorLightBlue
                            )
                        }
                    }
                }
            })
    }
}

@Composable
fun CustomTextArea(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        label = {
            Text(text = label, color = ChakraColorText)
        },
        modifier = Modifier
            .height(100.dp),
        value = value,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(10),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = ChakraColorLightBlue,
            unfocusedBorderColor = ChakraColorGray
        ),
        textStyle = TextStyle(
            color = ChakraColorText
        ),
        maxLines = 3,
    )
}

@Composable
fun CustomButton(
    label: String,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(45.dp)
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    listOf(
                        Color.Green.copy(alpha = .7f),
                        Color.Blue.copy(alpha = .7f)
                    )
                ),
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

@Preview
@Composable
fun TextAreaTextFieldPreview() {
    NoteAppTheme {
        Surface(
            modifier = Modifier
                .background(ChakraBackgroundColor)
                .padding(20.dp)
        ) {

            CustomTextArea(label = "Description", value = "Description", onValueChange = {})
        }
    }
}

@Composable
fun RadioButtonTaskType(
    selectedOption: TaskType,
    onOptionSelected: (TaskType) -> Unit = {}
) {

    Column {
        TaskType.values().forEach { taskType: TaskType ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(
                        selected = (taskType == selectedOption),
                        onClick = {
                            onOptionSelected(taskType)
                        }
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (taskType == selectedOption),
                    onClick = { onOptionSelected(taskType) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = ChakraColorLightBlue,
                        unselectedColor = ChakraColorGray
                    )
                )
                Text(
                    text = taskType.name.lowercase(),
                    color = ChakraColorText,
                    modifier = Modifier.padding(start = 7.dp)
                )
            }
        }
    }
}

fun Modifier.log(color: Color = Color.Red) = then(Modifier.border(width = 1.dp, color = color))