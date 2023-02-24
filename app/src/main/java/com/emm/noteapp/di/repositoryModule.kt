package com.emm.noteapp.di

import com.emm.noteapp.api.TaskRepository
import com.emm.noteapp.screens.home.data.TaskRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<TaskRepository> { TaskRepositoryImpl(get()) }
}