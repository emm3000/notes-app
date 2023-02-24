package com.emm.noteapp.screens.home.data

import com.emm.noteapp.api.TaskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Locale

class TaskRepositoryImpl(
    private val taskDao: TaskDao
) : TaskRepository {

    override suspend fun insertTask(task: Task) = withContext(Dispatchers.IO) {
        taskDao.insertTask(task.toEntity())
    }

    override fun loadTaskByType(type: Int): Flow<Pair<Int, List<Task>>> {
        return taskDao.loadTaskByType(type)
            .distinctUntilChanged()
            .map { Pair(type, it.toDomain()) }
    }
}

private fun Task.toEntity(): TaskEntity {
    return TaskEntity(
        id = id,
        title = title,
        description = description,
        type = type,
        taskCreateDate = taskCreateDate,
        taskUpdateDate = taskUpdateDate
    )
}

private fun TaskEntity.toDomain(): Task {
    return Task(
        id = id,
        title = title,
        description = description,
        type = type,
        taskCreateDate = taskCreateDate,
        taskUpdateDate = taskUpdateDate,
        timeAgo = taskCreateDate.formatDuration(),
        dateInString = taskCreateDate.dateToLegibleDate()
    )
}

fun Long.dateToLegibleDate(): String {
    val localDateTime = LocalDateTime.ofInstant(
        Instant.ofEpochMilli(this),
        ZoneId.systemDefault()
    )
    return localDateTime
        .format(
            DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
                .withLocale(Locale("es"))
        )
}

fun Long.formatDuration(): String {
    val duration = Duration.between(
        LocalDateTime.ofInstant(
            Instant.ofEpochMilli(this),
            ZoneId.systemDefault()
        ),
        LocalDateTime.now()
    )
    val seconds = duration.seconds
    return when {
        seconds < 60 -> "just now"
        seconds < 120 -> "1 minute"
        seconds < 3600 -> "${seconds / 60} minutes"
        seconds < 7200 -> "1 hour"
        seconds < 86400 -> "${seconds / 3600} hours"
        seconds < 172800 -> "1 day"
        else -> "${seconds / 86400} days"
    }
}


private fun List<TaskEntity>.toDomain(): List<Task> {
    return map(TaskEntity::toDomain)
}