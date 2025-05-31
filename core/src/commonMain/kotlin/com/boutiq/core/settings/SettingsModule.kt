package com.boutiq.core.settings

import org.koin.dsl.module

/**
 * Koin module for settings-related dependencies.
 */
val settingsModule = module {
    // The actual factory implementation is provided by platform-specific modules
    single<SettingsRepository> { SettingsRepositoryImpl(get<SettingsFactory>().createSettings()) }
}