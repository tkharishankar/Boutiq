package com.boutiq

import android.app.Application
import com.boutiq.core.settings.androidSettingsModule
import com.boutiq.di.initKoin

class BoutiqApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Initialize Koin
        initKoin {
            // Android specific configuration
            modules(androidSettingsModule + org.koin.dsl.module {
                single { applicationContext }
            })
        }
    }
}
