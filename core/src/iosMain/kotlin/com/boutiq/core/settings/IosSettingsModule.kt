package com.boutiq.core.settings

import org.koin.dsl.module

/**
 * iOS-specific Koin module for settings-related dependencies.
 */
val iosSettingsModule = module {
    single<SettingsFactory> { IosSettingsFactory() }
}