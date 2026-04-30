package com.petedianotech.aikeyboard

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class ThemeManager(context: Context) {
    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    
    companion object {
        const val THEME_LIGHT = "light"
        const val THEME_DARK = "dark"
        const val THEME_GOOGLE_LIGHT = "google_light"
        const val THEME_GOOGLE_DARK = "google_dark"
        const val THEME_MATERIAL_YOU = "material_you"
    }
    
    fun getCurrentTheme(): String = prefs.getString("keyboard_theme", THEME_LIGHT) ?: THEME_LIGHT
    
    fun setTheme(theme: String) {
        prefs.edit().putString("keyboard_theme", theme).apply()
    }
    
    fun getThemeColors(theme: String): ThemeColors {
        return when (theme) {
            THEME_LIGHT -> ThemeColors(
                backgroundColor = 0xFFFFFFFF.toInt(),
                keyBackgroundColor = 0xFFF5F5F5.toInt(),
                keyPressedColor = 0xFFE0E0E0.toInt(),
                textColor = 0xFF000000.toInt(),
                accentColor = 0xFF1F97F5.toInt(),
                shadowColor = 0x1A000000.toInt()
            )
            THEME_DARK -> ThemeColors(
                backgroundColor = 0xFF212121.toInt(),
                keyBackgroundColor = 0xFF424242.toInt(),
                keyPressedColor = 0xFF616161.toInt(),
                textColor = 0xFFFFFFFF.toInt(),
                accentColor = 0xFF4FC3F7.toInt(),
                shadowColor = 0x33000000.toInt()
            )
            THEME_GOOGLE_LIGHT -> ThemeColors(
                backgroundColor = 0xFFFAFAFA.toInt(),
                keyBackgroundColor = 0xFFE8F0FE.toInt(),
                keyPressedColor = 0xFFD2E3FC.toInt(),
                textColor = 0xFF1F2937.toInt(),
                accentColor = 0xFF4285F4.toInt(),
                shadowColor = 0x0D000000.toInt()
            )
            THEME_GOOGLE_DARK -> ThemeColors(
                backgroundColor = 0xFF1F1F1F.toInt(),
                keyBackgroundColor = 0xFF2C3E50.toInt(),
                keyPressedColor = 0xFF3D5A80.toInt(),
                textColor = 0xFFE8E8E8.toInt(),
                accentColor = 0xFF8AB4F8.toInt(),
                shadowColor = 0x1A000000.toInt()
            )
            THEME_MATERIAL_YOU -> ThemeColors(
                backgroundColor = 0xFFFFFBFE.toInt(),
                keyBackgroundColor = 0xFFF3E5F5.toInt(),
                keyPressedColor = 0xFFE1BEE7.toInt(),
                textColor = 0xFF1B1B1F.toInt(),
                accentColor = 0xFFD946EF.toInt(),
                shadowColor = 0x1A000000.toInt()
            )
            else -> getThemeColors(THEME_LIGHT)
        }
    }
}

data class ThemeColors(
    val backgroundColor: Int,
    val keyBackgroundColor: Int,
    val keyPressedColor: Int,
    val textColor: Int,
    val accentColor: Int,
    val shadowColor: Int
)
