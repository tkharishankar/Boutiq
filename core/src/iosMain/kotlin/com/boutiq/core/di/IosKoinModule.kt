package com.boutiq.core.di

import com.boutiq.core.settings.iosSettingsModule
import org.koin.dsl.module

/**
 * List of all core modules for iOS platform.
 */
val allIosCoreModules = allCoreModules + listOf(iosSettingsModule)