package com.emm.noteapp.screens.home

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.emm.noteapp.ui.components.CustomButton
import com.emm.noteapp.ui.components.CustomTextArea
import com.emm.noteapp.ui.components.CustomTextField
import com.emm.noteapp.ui.components.RadioButtonTaskType
import com.emm.noteapp.ui.theme.ChakraBackgroundColor
import com.emm.noteapp.ui.theme.ChakraColorLightBlue
import com.emm.noteapp.ui.theme.ChakraColorText
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import kotlin.random.Random

enum class TaskType {
    PAST,
    CURRENT,
    FUTURE
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeScreen() {

    val pagerState = rememberPagerState()

    var showDialog by remember {
        mutableStateOf(false)
    }

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    if (showDialog) {
        Dialog(
            onDismissRequest = { showDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
//                    .fillMaxHeight((screenHeight.value * 0.9f) / 1000)
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
                    CustomTextField(value = "", label = "Title") {

                    }
                    CustomTextArea(
                        label = "Description",
                        value = "Description",
                        onValueChange = {}
                    )
                    RadioButtonTaskType(
                        selectedOption = TaskType.CURRENT,
                        onOptionSelected = {}
                    )
                    CustomButton(label = "Add Task") {

                    }

                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ChakraBackgroundColor)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            CustomTabs(pagerState)
            HorizontalPager(count = TaskType.values().size, state = pagerState) { page: Int ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Color(
                                Random.nextFloat(),
                                Random.nextFloat(),
                                Random.nextFloat(),
                            )
                        )
                ) {
                    Text(text = TaskType.values()[page].name)
                }
            }
        }
        FloatingActionButton(
            onClick = { showDialog = true },
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.BottomEnd),
            elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
            backgroundColor = ChakraColorLightBlue
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun CustomTabs(pagerState: PagerState) {
    Row(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TaskType.values().forEachIndexed { index, taskTab ->
            CustomTab(pagerState, taskTab.name, index)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun RowScope.CustomTab(
    pagerState: PagerState,
    title: String,
    position: Int
) {

    val color: Animatable<Color, AnimationVector4D> = remember {
        Animatable(ChakraBackgroundColor)
    }

    val coroutine = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        color.animateTo(
            if (pagerState.currentPage == position) ChakraColorLightBlue else ChakraBackgroundColor
        )
    }

    Column(
        modifier = Modifier.Companion
            .weight(1f)
            .clickable {
                coroutine.launch {
                    pagerState.animateScrollToPage(position)
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = title, color = ChakraColorText, textAlign = TextAlign.Center)
        Box(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth(.7f)
                .background(color.value)
        )

    }
}

