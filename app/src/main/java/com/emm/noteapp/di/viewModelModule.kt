package com.emm.noteapp.di

import com.emm.noteapp.activities.main.MainViewModel
import com.emm.noteapp.screens.home.HomeViewModel
import com.emm.noteapp.screens.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::MainViewModel)
}