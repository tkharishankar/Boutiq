package com.boutiq.core.settings

/**
 * Interface for managing key-value preferences across platforms.
 */
interface SettingsRepository {
    /**
     * Saves a boolean value with the given key.
     */
    fun saveBoolean(key: String, value: Boolean)
    
    /**
     * Retrieves a boolean value for the given key.
     * Returns the default value if the key doesn't exist.
     */
    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean
    
    /**
     * Saves a string value with the given key.
     */
    fun saveString(key: String, value: String)
    
    /**
     * Retrieves a string value for the given key.
     * Returns the default value if the key doesn't exist.
     */
    fun getString(key: String, defaultValue: String = ""): String
    
    /**
     * Saves an integer value with the given key.
     */
    fun saveInt(key: String, value: Int)
    
    /**
     * Retrieves an integer value for the given key.
     * Returns the default value if the key doesn't exist.
     */
    fun getInt(key: String, defaultValue: Int = 0): Int
    
    /**
     * Removes a value with the given key.
     */
    fun remove(key: String)
    
    /**
     * Clears all values.
     */
    fun clear()
}