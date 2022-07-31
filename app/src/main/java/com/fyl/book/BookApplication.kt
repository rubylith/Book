package com.fyl.book

import android.app.Application
import android.util.Log.DEBUG
import android.util.Log.ERROR
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BookApplication : Application(), Configuration.Provider {
    override fun getWorkManagerConfiguration(): Configuration =
        Configuration.Builder()
            .setMinimumLoggingLevel(if (BuildConfig.DEBUG) DEBUG else ERROR)
            .build()
}