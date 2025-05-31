package com.boutiq.core.di

import com.boutiq.core.settings.androidSettingsModule
import org.koin.dsl.module

/**
 * List of all core modules for Android platform.
 */
val allAndroidCoreModules = allCoreModules + listOf(androidSettingsModule)