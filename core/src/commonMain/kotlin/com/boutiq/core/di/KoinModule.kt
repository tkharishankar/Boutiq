package com.boutiq.core.di

import com.boutiq.core.Greeting
import com.boutiq.core.network.ApiService
import com.boutiq.core.network.KtorHttpClient
import com.boutiq.core.network.NetworkClient
import com.boutiq.core.session.sessionModule
import com.boutiq.core.settings.settingsModule
import org.koin.dsl.module

val coreModule = module {
    single { Greeting() }

    single<NetworkClient> { KtorHttpClient() }

    single { ApiService(get()) }
}

// List of all core modules
val allCoreModules = listOf(coreModule, settingsModule, sessionModule)
