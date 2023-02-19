package com.emm.myapplication.di

import com.emm.myapplication.activities.main.MainViewModel
import com.emm.myapplication.screens.home.HomeViewModel
import com.emm.myapplication.screens.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::MainViewModel)
}