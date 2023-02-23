package com.emm.noteapp.screens.home

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.emm.noteapp.screens.home.components.TaskDialog
import com.emm.noteapp.screens.home.state.HomeState
import com.emm.noteapp.ui.theme.ChakraBackgroundColor
import com.emm.noteapp.ui.theme.ChakraColorLightBlue
import com.emm.noteapp.ui.theme.ChakraColorText
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.random.Random

enum class TaskType {
    PAST,
    CURRENT,
    FUTURE
}

@Composable
fun HomeScreen(vm: HomeViewModel = koinViewModel()) {

    val state: State<HomeState> = vm.homeState.collectAsState()

    HomeScreen(
        titleValue = state.value.title,
        descriptionValue = state.value.description,
        selectionOption = state.value.type,
        onSelectionOptionChange = vm::onSelectedTypeTaskChange,
        onTitleChange = vm::onTitleChange,
        onDescriptionChange = vm::onDescriptionChange,
        onAddTaskClick = vm::onSaveTask
    )
}

@Composable
@OptIn(ExperimentalPagerApi::class)
private fun HomeScreen(
    titleValue: String,
    descriptionValue: String,
    selectionOption: TaskType,
    onSelectionOptionChange: (TaskType) -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onAddTaskClick: () -> Unit
) {
    val pagerState = rememberPagerState()

    var showDialog by remember {
        mutableStateOf(false)
    }

    if (showDialog) {
        TaskDialog(
            titleValue,
            descriptionValue = descriptionValue,
            selectionOption = selectionOption,
            onSelectionOptionChange = onSelectionOptionChange,
            onTitleChange = onTitleChange,
            onDescriptionChange = onDescriptionChange,
            onDismissRequest = {
                showDialog = false
            },
            onAddTaskClick = onAddTaskClick
        )
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

