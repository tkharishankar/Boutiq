package com.boutiq.core.settings

import com.russhwolf.settings.Settings

/**
 * Factory interface for creating platform-specific Settings instances.
 */
interface SettingsFactory {
    /**
     * Creates a Settings instance.
     */
    fun createSettings(): Settings
}