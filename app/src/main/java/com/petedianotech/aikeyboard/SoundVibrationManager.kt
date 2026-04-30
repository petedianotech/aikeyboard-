package com.petedianotech.aikeyboard

import android.content.Context
import android.media.AudioManager
import android.os.Vibrator
import android.os.VibratorManager
import android.os.Build
import android.preference.PreferenceManager

class SoundVibrationManager(private val context: Context) {
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)
    
    private val vibrator: Vibrator? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        @Suppress("DEPRECATION")
        context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }
    
    fun playKeyTap() {
        if (isSoundEnabled()) {
            audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK)
        }
    }
    
    fun vibrateKey() {
        if (isVibrationEnabled()) {
            vibrator?.vibrate(10) // 10ms short vibration
        }
    }
    
    fun vibrateLongPress() {
        if (isVibrationEnabled()) {
            vibrator?.vibrate(30) // 30ms longer vibration
        }
    }
    
    fun isSoundEnabled(): Boolean = prefs.getBoolean("keyboard_sound_enabled", true)
    
    fun isVibrationEnabled(): Boolean = prefs.getBoolean("keyboard_vibration_enabled", true)
    
    fun setSoundEnabled(enabled: Boolean) {
        prefs.edit().putBoolean("keyboard_sound_enabled", enabled).apply()
    }
    
    fun setVibrationEnabled(enabled: Boolean) {
        prefs.edit().putBoolean("keyboard_vibration_enabled", enabled).apply()
    }
}
