package com.usmonie.word.features.dashboard.data.repository

import com.liftric.kvault.KVault
import com.usmonie.word.features.dashboard.domain.models.Theme
import com.usmonie.word.features.dashboard.domain.repository.UserRepository

class UserRepositoryImpl(private val kVault: KVault): UserRepository {
    
    override var currentTheme: Theme
        set(value) {
            value.colorsName?.let { kVault.set(CURRENT_USER_THEME_COLORS_KEY, it) }
            value.fonts?.let { kVault.set(CURRENT_USER_THEME_FONTS_KEY, it) }
        }
        get() = Theme(kVault.string(CURRENT_USER_THEME_COLORS_KEY) , kVault.string(
            CURRENT_USER_THEME_FONTS_KEY
        ))
    
    companion object {
        private const val CURRENT_USER_THEME_COLORS_KEY: String = "CURRENT_USER_THEME_COLORS"
        private const val CURRENT_USER_THEME_FONTS_KEY: String = "CURRENT_USER_THEME_FONTS"
    }
}
