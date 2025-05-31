# 🚀 Compose Multiplatform App

A modern, feature-rich mobile application built with **Kotlin Multiplatform** and **Compose Multiplatform**, delivering native performance across Android and iOS platforms.

## ✨ Features

- 🔐 **Complete Authentication Flow** - Secure login, registration, and user management
- 📊 **Interactive Dashboard** - Real-time data visualization and analytics
- 🎨 **Material Design 3** - Beautiful, consistent UI across platforms
- 🌐 **Cross-Platform** - Single codebase for Android and iOS
- ⚡ **Native Performance** - Leveraging platform-specific optimizations

## 🏗️ Architecture

This project follows **Clean Architecture** principles with a modular approach:

```
📦 Project Structure
├── 🎨 composeApp/          # Main UI layer (Compose Multiplatform)
├── ⚙️ core/                # Shared business logic & utilities
├── 🔐 feature-auth/        # Authentication module
├── 📊 feature-dashboard/   # Dashboard & analytics module
├── 📱 iosApp/              # iOS-specific implementations
└── 🔧 gradle/              # Build configuration
```

### 🎯 Module Responsibilities

| Module | Purpose | Technologies |
|--------|---------|-------------|
| **composeApp** | UI layer with shared screens and navigation | Compose Multiplatform, Material Design 3 |
| **core** | Business logic, data models, and utilities | KMP, Coroutines, Serialization |
| **feature-auth** | Authentication logic and screens | Ktor, Secure Storage, Validation |
| **feature-dashboard** | Dashboard functionality and data visualization | Charts, Real-time updates |
| **iosApp** | iOS-specific code and integrations | Swift, iOS APIs |

## 🛠️ Tech Stack

### **Core Technologies**
- **[Kotlin Multiplatform](https://kotlinlang.org/multiplatform/)** - Shared business logic
- **[Compose Multiplatform](https://www.jetbrains.com/compose-multiplatform/)** - UI framework
- **[Material Design 3](https://m3.material.io/)** - Design system

### **Networking & Data**
- **[Ktor](https://ktor.io/)** - HTTP client for API communication
- **[Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)** - JSON parsing
- **[Multiplatform Settings](https://github.com/russhwolf/multiplatform-settings)** - Shared preferences

### **Dependency Injection & Architecture**
- **[Koin](https://insert-koin.io/)** - Dependency injection
- **[Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)** - Asynchronous programming
