package com.emm.noteapp.application

import android.app.Application
import com.emm.noteapp.di.dbModule
import com.emm.noteapp.di.repositoryModule
import com.emm.noteapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                viewModelModule, dbModule, repositoryModule
            )
        }
    }
}