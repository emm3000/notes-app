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
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.emm.noteapp.screens.home.components.TaskDialog
import com.emm.noteapp.screens.home.data.Task
import com.emm.noteapp.screens.home.state.HomeState
import com.emm.noteapp.screens.home.state.TaskType
import com.emm.noteapp.ui.theme.ChakraBackgroundColor
import com.emm.noteapp.ui.theme.ChakraColorGray
import com.emm.noteapp.ui.theme.ChakraColorLightBlue
import com.emm.noteapp.ui.theme.ChakraColorText
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

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
        onAddTaskClick = vm::onSaveTask,
        pastTaskList = vm.pastTaskList,
        currentTaskList = vm.currentTaskList,
        futureTaskList = vm.futureTaskList
    )
}

@Composable
private fun HomeScreen(
    titleValue: String,
    descriptionValue: String,
    selectionOption: TaskType,
    onSelectionOptionChange: (TaskType) -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onAddTaskClick: () -> Unit,
    pastTaskList: List<Task>,
    currentTaskList: List<Task>,
    futureTaskList: List<Task>,
) {

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
            onDismissRequest = { showDialog = false },
            onAddTaskClick = {
                showDialog = false
                onAddTaskClick()
            }
        )
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ChakraBackgroundColor)
    ) {
        TaskPager(
            pastTaskList = pastTaskList,
            currentTaskList = currentTaskList,
            futureTaskList = futureTaskList
        )
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

@Composable
@OptIn(ExperimentalPagerApi::class)
private fun TaskPager(
    pastTaskList: List<Task>,
    currentTaskList: List<Task>,
    futureTaskList: List<Task>
) {
    val pagerState = rememberPagerState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        CustomTabs(pagerState)
        HorizontalPager(count = TaskType.values().size, state = pagerState) { page: Int ->
            when (page) {
                0 -> PagerItem(pastTaskList)
                1 -> PagerItem(currentTaskList)
                2 -> PagerItem(futureTaskList)
            }
        }
    }
}

@Composable
private fun PagerItem(
    tasks: List<Task>
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp)
        ) {
            items(tasks) { task ->
                SimpleItemTask(task)
            }
            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@Composable
fun SimpleItemTask(
    task: Task
) {
    val (isNew, setIsNew) = remember {
        mutableStateOf(false)
    }

    val color = remember { Animatable(Color.Gray) }

    LaunchedEffect(isNew) {
        color.animateTo(if (isNew) Color.Green else ChakraColorGray)
    }

    LaunchedEffect(task) {
        if ((System.currentTimeMillis() - task.taskCreateDate) < 2500L) {
            setIsNew(true)
        }
        delay(3500)
        setIsNew(false)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min) // Interesante
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(50f))
            .border(
                width = 1.dp,
                color = color.value,
                shape = RoundedCornerShape(50f)
            )
            .padding(horizontal = 25.dp, vertical = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(2f)
        ) {
            Text(
                text = task.title,
                color = ChakraColorText,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = task.description.ifEmpty { "-" },
                fontSize = 14.sp,
                color = ChakraColorText.copy(alpha = .5f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = task.dateInString,
                color = ChakraColorText.copy(alpha = .4f),
                fontSize = 11.sp
            )
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = task.timeAgo,
                fontSize = 10.sp,
                color = ChakraColorText.copy(alpha = .6f)
            )
        }
    }


}

@OptIn(ExperimentalPagerApi::class)
@Composable
private fun CustomTabs(pagerState: PagerState) {
    Column {
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
        Divider(
            color = ChakraColorGray
        )
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
        modifier = Modifier
            .fillMaxHeight()
            .weight(1f)
            .clickable {
                coroutine.launch {
                    pagerState.animateScrollToPage(position)
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            color = ChakraColorText,
            textAlign = TextAlign.Center,
            fontWeight = if (pagerState.currentPage == position) FontWeight.Bold else null,
            fontSize = if (pagerState.currentPage == position) 15.sp else 14.sp,
        )
        Box(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth(.7f)
                .background(color.value)
        )

    }
}

