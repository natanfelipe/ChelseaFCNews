package com.br.natanfelipe.chelseafcnews

import android.app.Application
import com.br.natanfelipe.chelseafcnews.di.appModule
import com.br.natanfelipe.chelseafcnews.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ChelseaNewsApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ChelseaNewsApplication)
            modules(appModule, networkModule)
        }
    }
}