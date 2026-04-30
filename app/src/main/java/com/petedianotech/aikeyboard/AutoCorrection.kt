package com.petedianotech.aikeyboard

import android.content.Context

class AutoCorrection(context: Context) {
    private val commonCorrections = mapOf(
        "teh" to "the",
        "recieve" to "receive",
        "occured" to "occurred",
        "adress" to "address",
        "seperate" to "separate",
        "thier" to "their",
        "wich" to "which",
        "bussiness" to "business",
        "definately" to "definitely",
        "neccessary" to "necessary",
        "accomodate" to "accommodate",
        "concieve" to "conceive",
        "dissapear" to "disappear",
        "embarass" to "embarrass",
        "existance" to "existence",
        "Teh" to "The",
        "Recieve" to "Receive",
        "Occured" to "Occurred",
        "Adress" to "Address"
    )
    
    private val prefs = android.preference.PreferenceManager.getDefaultSharedPreferences(context)
    
    fun isEnabled(): Boolean = prefs.getBoolean("auto_correction_enabled", true)
    
    fun correctText(text: String): String {
        if (!isEnabled()) return text
        
        var result = text
        commonCorrections.forEach { (incorrect, correct) ->
            result = result.replace(Regex("\\b$incorrect\\b"), correct)
        }
        return result
    }
    
    fun getLastWord(text: String, offset: Int = 0): String {
        if (text.isEmpty()) return ""
        val trimmed = text.substring(0, minOf(text.length, text.length - offset))
        val words = trimmed.split(Regex("[\\s\\p{P}]"))
        return words.lastOrNull()?.trim() ?: ""
    }
    
    fun suggestCorrection(word: String): String? {
        return commonCorrections[word]
    }
}
