package com.emm.noteapp.di

import android.content.Context
import androidx.room.Room
import com.emm.noteapp.screens.home.data.TaskDao
import com.emm.noteapp.core.TaskDataBase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dbModule = module {
    single { provideTaskDataBase(androidApplication()) }
    single { provideTaskDao(get()) }
}

fun provideTaskDataBase(context: Context): TaskDataBase {
    return Room.databaseBuilder(
        context,
        TaskDataBase::class.java,
        "Task.db"
    )
        .fallbackToDestructiveMigration()
        .build()
}

fun provideTaskDao(taskDataBase: TaskDataBase): TaskDao {
    return taskDataBase.taskDao()
}