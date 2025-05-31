package com.boutiq.core.session

import com.boutiq.core.settings.SettingsRepository

/**
 * Manager for handling user session state.
 */
class SessionManager(private val settingsRepository: SettingsRepository) {
    
    companion object {
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_AUTH_TOKEN = "auth_token"
    }
    
    /**
     * Saves the user session after successful login.
     */
    fun saveSession(userId: String, userName: String, authToken: String) {
        settingsRepository.saveBoolean(KEY_IS_LOGGED_IN, true)
        settingsRepository.saveString(KEY_USER_ID, userId)
        settingsRepository.saveString(KEY_USER_NAME, userName)
        settingsRepository.saveString(KEY_AUTH_TOKEN, authToken)
    }
    
    /**
     * Checks if the user is logged in.
     */
    fun isLoggedIn(): Boolean {
        return settingsRepository.getBoolean(KEY_IS_LOGGED_IN, false)
    }
    
    /**
     * Gets the user ID.
     */
    fun getUserId(): String {
        return settingsRepository.getString(KEY_USER_ID, "")
    }
    
    /**
     * Gets the user name.
     */
    fun getUserName(): String {
        return settingsRepository.getString(KEY_USER_NAME, "")
    }
    
    /**
     * Gets the authentication token.
     */
    fun getAuthToken(): String {
        return settingsRepository.getString(KEY_AUTH_TOKEN, "")
    }
    
    /**
     * Clears the user session on logout.
     */
    fun clearSession() {
        settingsRepository.saveBoolean(KEY_IS_LOGGED_IN, false)
        settingsRepository.remove(KEY_USER_ID)
        settingsRepository.remove(KEY_USER_NAME)
        settingsRepository.remove(KEY_AUTH_TOKEN)
    }
}