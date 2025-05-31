package com.boutiq.core.settings

import org.koin.dsl.module

/**
 * Android-specific Koin module for settings-related dependencies.
 */
val androidSettingsModule = module {
    single<SettingsFactory> { AndroidSettingsFactory(get()) }
}