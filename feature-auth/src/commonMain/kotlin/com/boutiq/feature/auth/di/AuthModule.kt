package com.boutiq.feature.auth.di

import com.boutiq.core.session.SessionManager
import com.boutiq.feature.auth.api.AuthApiService
import com.boutiq.feature.auth.viewmodel.AuthViewModel
import org.koin.dsl.module

val authModule = module {
    single { AuthApiService(get()) }

    factory { AuthViewModel(get<AuthApiService>(), get<SessionManager>()) }
}
