package com.boutiq.core.settings

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

/**
 * Android implementation of SettingsFactory using SharedPreferences.
 */
class AndroidSettingsFactory(private val context: Context) : SettingsFactory {
    override fun createSettings(): Settings {
        val sharedPreferences = context.getSharedPreferences("boutiq_preferences", Context.MODE_PRIVATE)
        return SharedPreferencesSettings(sharedPreferences)
    }
}