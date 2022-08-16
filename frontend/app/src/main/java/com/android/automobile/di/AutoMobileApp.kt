package com.android.automobile.di

import android.app.Application
import android.content.Context
import com.android.automobile.data.repository.ProductRepository
import com.android.automobile.data.source.local.AppDatabase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
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
