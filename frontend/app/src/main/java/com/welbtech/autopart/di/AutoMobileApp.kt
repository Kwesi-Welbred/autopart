package com.welbtech.autopart.di

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AutoMobileApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        Timber.DebugTree()
    }

    companion object {
        var appContext: Context? = null
            private set
    }
}
