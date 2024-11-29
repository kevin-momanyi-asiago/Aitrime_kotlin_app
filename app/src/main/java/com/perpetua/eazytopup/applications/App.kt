package com.perpetua.eazytopup.applications

import android.app.Application
import com.perpetua.eazytopup.modules.repositoryModule
import com.perpetua.eazytopup.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(viewModelModule, repositoryModule))
        }
    }
}