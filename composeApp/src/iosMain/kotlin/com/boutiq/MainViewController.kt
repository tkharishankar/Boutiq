package com.boutiq

import androidx.compose.ui.window.ComposeUIViewController
import com.boutiq.core.settings.iosSettingsModule
import com.boutiq.di.initKoin

// Initialize Koin for iOS
private val koinApp = initKoin {
    // iOS specific configuration
    modules(iosSettingsModule)
}

fun MainViewController() = ComposeUIViewController { App() }
