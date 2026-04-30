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
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        prefs = PreferenceManager.getDefaultSharedPreferences(this)

        apiKeyEdit = findViewById(R.id.api_key_edit)
        toneSpinner = findViewById(R.id.tone_spinner)
        saveButton = findViewById(R.id.save_button)

        // Load current values
        apiKeyEdit.setText(prefs.getString("api_key", ""))

        val tones = arrayOf("formal", "casual", "professional")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tones)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        toneSpinner.adapter = adapter

        val currentTone = prefs.getString("selected_tone", "formal") ?: "formal"
        val position = tones.indexOf(currentTone)
        if (position >= 0) {
            toneSpinner.setSelection(position)
        }

        saveButton.setOnClickListener {
            val apiKey = apiKeyEdit.text.toString().trim()
            val selectedTone = toneSpinner.selectedItem.toString()

            prefs.edit()
                .putString("api_key", apiKey)
                .putString("selected_tone", selectedTone)
                .apply()

            Toast.makeText(this, "Settings saved", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}