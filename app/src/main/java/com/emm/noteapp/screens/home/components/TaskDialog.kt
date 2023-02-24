package com.emm.noteapp.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.emm.noteapp.screens.home.state.TaskType
import com.emm.noteapp.ui.components.CustomButton
import com.emm.noteapp.ui.components.CustomTextArea
import com.emm.noteapp.ui.components.CustomTextField
import com.emm.noteapp.ui.components.RadioButtonTaskType
import com.emm.noteapp.ui.theme.ChakraBackgroundColor
import com.emm.noteapp.ui.theme.ChakraColorLightBlue

@Composable
fun TaskDialog(
    titleValue: String,
    descriptionValue: String,
    selectionOption: TaskType,
    onSelectionOptionChange: (TaskType) -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onAddTaskClick: () -> Unit
) {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .heightIn(max = screenHeight * 0.7f)
                .clip(RoundedCornerShape(50f))
                .background(ChakraBackgroundColor)
                .border(
                    width = 1.dp,
                    color = ChakraColorLightBlue,
                    shape = RoundedCornerShape(50f)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(state = rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 30.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                CustomTextField(
                    value = titleValue,
                    label = "Title",
                    onValueChange = onTitleChange
                )
                CustomTextArea(
                    label = "Description",
                    value = descriptionValue,
                    onValueChange = onDescriptionChange
                )
                RadioButtonTaskType(
                    selectedOption = selectionOption,
                    onOptionSelected = onSelectionOptionChange
                )
                CustomButton(label = "Add Task", onClick = onAddTaskClick)
            }
        }
    }
}