package com.boutiq.di

import com.boutiq.core.di.allCoreModules
import com.boutiq.feature.auth.di.authModule
import com.boutiq.feature.dashboard.di.dashboardModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

/**
 * Initialize Koin for the application
 */
fun initKoin(appDeclaration: KoinAppDeclaration = {}) {
    startKoin {
        appDeclaration()
        modules(
            allCoreModules + listOf(authModule, dashboardModule)
        )
    }
}
