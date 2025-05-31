package com.boutiq.core

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform