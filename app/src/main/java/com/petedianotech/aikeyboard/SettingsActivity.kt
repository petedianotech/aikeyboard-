package com.petedianotech.aikeyboard

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.*

class SettingsActivity : Activity() {

    private lateinit var prefs: SharedPreferences
    private lateinit var apiKeyEdit: EditText
    private lateinit var toneSpinner: Spinner
    private lateinit var themeSpinner: Spinner
    private lateinit var soundToggle: CheckBox
    private lateinit var vibrationToggle: CheckBox
    private lateinit var autoCorrectionToggle: CheckBox
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        prefs = PreferenceManager.getDefaultSharedPreferences(this)

        apiKeyEdit = findViewById(R.id.api_key_edit)
        toneSpinner = findViewById(R.id.tone_spinner)
        themeSpinner = findViewById(R.id.theme_spinner)
        soundToggle = findViewById(R.id.sound_toggle)
        vibrationToggle = findViewById(R.id.vibration_toggle)
        autoCorrectionToggle = findViewById(R.id.auto_correction_toggle)
        saveButton = findViewById(R.id.save_button)

        // Load current values
        apiKeyEdit.setText(prefs.getString("api_key", ""))
        soundToggle.isChecked = prefs.getBoolean("keyboard_sound_enabled", true)
        vibrationToggle.isChecked = prefs.getBoolean("keyboard_vibration_enabled", true)
        autoCorrectionToggle.isChecked = prefs.getBoolean("auto_correction_enabled", true)

        val tones = arrayOf("formal", "casual", "professional")
        val toneAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tones)
        toneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        toneSpinner.adapter = toneAdapter

        val currentTone = prefs.getString("selected_tone", "formal") ?: "formal"
        val tonePosition = tones.indexOf(currentTone)
        if (tonePosition >= 0) {
            toneSpinner.setSelection(tonePosition)
        }

        // Setup themes
        val themes = arrayOf("Light", "Dark", "Google Light", "Google Dark", "Material You")
        val themeValues = arrayOf("light", "dark", "google_light", "google_dark", "material_you")
        val themeAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, themes)
        themeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        themeSpinner.adapter = themeAdapter

        val currentTheme = prefs.getString("keyboard_theme", "light") ?: "light"
        val themePosition = themeValues.indexOf(currentTheme)
        if (themePosition >= 0) {
            themeSpinner.setSelection(themePosition)
        }

        saveButton.setOnClickListener {
            val apiKey = apiKeyEdit.text.toString().trim()
            val selectedTone = toneSpinner.selectedItem.toString()
            val selectedThemeValue = themeValues[themeSpinner.selectedItemPosition]

            prefs.edit()
                .putString("api_key", apiKey)
                .putString("selected_tone", selectedTone)
                .putString("keyboard_theme", selectedThemeValue)
                .putBoolean("keyboard_sound_enabled", soundToggle.isChecked)
                .putBoolean("keyboard_vibration_enabled", vibrationToggle.isChecked)
                .putBoolean("auto_correction_enabled", autoCorrectionToggle.isChecked)
                .apply()

            Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}