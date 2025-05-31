package com.boutiq.core.settings

import com.russhwolf.settings.Settings

/**
 * Implementation of SettingsRepository using multiplatform-settings library.
 */
class SettingsRepositoryImpl(private val settings: Settings) : SettingsRepository {
    
    override fun saveBoolean(key: String, value: Boolean) {
        settings.putBoolean(key, value)
    }
    
    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return settings.getBoolean(key, defaultValue)
    }
    
    override fun saveString(key: String, value: String) {
        settings.putString(key, value)
    }
    
    override fun getString(key: String, defaultValue: String): String {
        return settings.getString(key, defaultValue)
    }
    
    override fun saveInt(key: String, value: Int) {
        settings.putInt(key, value)
    }
    
    override fun getInt(key: String, defaultValue: Int): Int {
        return settings.getInt(key, defaultValue)
    }
    
    override fun remove(key: String) {
        settings.remove(key)
    }
    
    override fun clear() {
        settings.clear()
    }
}