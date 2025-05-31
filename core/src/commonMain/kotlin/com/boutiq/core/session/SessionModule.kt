package com.boutiq.core.session

import org.koin.dsl.module

/**
 * Koin module for session-related dependencies.
 */
val sessionModule = module {
    single { SessionManager(get()) }
}