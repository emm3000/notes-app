package com.emm.noteapp.core

import androidx.room.Database
import androidx.room.RoomDatabase
import com.emm.noteapp.screens.home.data.TaskDao
import com.emm.noteapp.screens.home.data.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 4,
    exportSchema = false
)
abstract class TaskDataBase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

}