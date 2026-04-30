package com.petedianotech.aikeyboard

import android.inputmethodservice.InputMethodService
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputConnection
import android.widget.Toast
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import android.content.SharedPreferences
import android.preference.PreferenceManager

class AIKeyboardService : InputMethodService(), KeyboardView.OnKeyboardActionListener {

    private lateinit var keyboardView: KeyboardView
    private lateinit var keyboard: Keyboard
    private val client = OkHttpClient()
    private lateinit var prefs: SharedPreferences

    private var selectedTone = "formal" // default tone

    override fun onCreate() {
        super.onCreate()
        prefs = PreferenceManager.getDefaultSharedPreferences(this)
        selectedTone = prefs.getString("selected_tone", "formal") ?: "formal"
    }

    override fun onCreateInputView(): View {
        keyboardView = layoutInflater.inflate(R.layout.keyboard_view, null) as KeyboardView
        keyboard = Keyboard(this, R.xml.qwerty)
        keyboardView.keyboard = keyboard
        keyboardView.setOnKeyboardActionListener(this)
        return keyboardView
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        val inputConnection = currentInputConnection ?: return

        when (primaryCode) {
            Keyboard.KEYCODE_DELETE -> {
                val selectedText = inputConnection.getSelectedText(0)
                if (TextUtils.isEmpty(selectedText)) {
                    inputConnection.deleteSurroundingText(1, 0)
                } else {
                    inputConnection.commitText("", 1)
                }
            }
            Keyboard.KEYCODE_SHIFT -> {
                // Handle shift - could toggle caps
            }
            Keyboard.KEYCODE_DONE -> {
                inputConnection.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
            }
            -2 -> { // AI button
                if (keyCodes?.get(0) == KeyEvent.KEYCODE_UNKNOWN) {
                    // Long press - cycle tone
                    showToneSelector()
                } else {
                    processAI()
                }
            }
            else -> {
                val char = primaryCode.toChar()
                inputConnection.commitText(char.toString(), 1)
            }
        }
    }

    private fun processAI() {
        val inputConnection = currentInputConnection ?: return
        val text = inputConnection.getExtractedText(android.view.inputmethod.ExtractedTextRequest(), 0)?.text?.toString() ?: ""
        if (text.isNotEmpty()) {
            // Cache check
            val cacheKey = "$text|$selectedTone"
            val cached = prefs.getString(cacheKey, null)
            if (cached != null) {
                inputConnection.setComposingText(cached, 1)
                inputConnection.finishComposingText()
                return
            }
            rewriteText(text, selectedTone, cacheKey)
        }
    }

    private fun rewriteText(text: String, tone: String, cacheKey: String) {
        val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        scope.launch {
            try {
                val rewritten = callCerebrasAPI(text, tone)
                prefs.edit().putString(cacheKey, rewritten).apply()
                withContext(Dispatchers.Main) {
                    val inputConnection = currentInputConnection
                    inputConnection?.setComposingText(rewritten, 1)
                    inputConnection?.finishComposingText()
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@AIKeyboardService, "AI rewrite failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private suspend fun callCerebrasAPI(text: String, tone: String): String {
        return suspendCancellableCoroutine { continuation ->
            val apiKey = prefs.getString("api_key", "") ?: ""
            if (apiKey.isEmpty()) {
                continuation.resumeWithException(IOException("API key not set. Please set it in settings."))
                return@suspendCancellableCoroutine
            }

            val prompt = "Rewrite the following text with correct grammar and punctuation in a $tone tone. Only return the rewritten text, no explanations or additional content: $text"

            val json = JSONObject().apply {
                put("model", "llama3.1-8b")
                put("prompt", prompt)
                put("max_tokens", 1000)
                put("temperature", 0.3)
                put("stream", false)
            }
            val body = json.toString().toRequestBody("application/json".toMediaType())

            val request = Request.Builder()
                .url("https://api.cerebras.ai/v1/completions")
                .post(body)
                .addHeader("Authorization", "Bearer $apiKey")
                .addHeader("Content-Type", "application/json")
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    continuation.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful) {
                            continuation.resumeWithException(IOException("HTTP ${response.code}: ${response.message}"))
                        } else {
                            val responseBody = response.body?.string()
                            try {
                                val jsonResponse = JSONObject(responseBody ?: "")
                                val choices = jsonResponse.optJSONArray("choices")
                                val rewritten = choices?.optJSONObject(0)?.optString("text", text)?.trim() ?: text
                                continuation.resume(rewritten)
                            } catch (e: Exception) {
                                continuation.resumeWithException(IOException("Invalid response format"))
                            }
                        }
                    }
                }
            })
        }
    }

    private fun showToneSelector() {
        val tones = arrayOf("formal", "casual", "professional")
        selectedTone = tones[(tones.indexOf(selectedTone) + 1) % tones.size]
        prefs.edit().putString("selected_tone", selectedTone).apply()
        Toast.makeText(this, "Tone: $selectedTone", Toast.LENGTH_SHORT).show()
    }

    private var isLongPress = false

    override fun onPress(primaryCode: Int) {
        if (primaryCode == -2) {
            isLongPress = false
            // Start long press detection
            keyboardView.postDelayed({
                isLongPress = true
                showToneSelector()
            }, 500) // 500ms for long press
        }
    }

    override fun onRelease(primaryCode: Int) {
        if (primaryCode == -2) {
            keyboardView.removeCallbacks(null) // Cancel long press
            if (!isLongPress) {
                processAI()
            }
        }
    }
    override fun onText(text: CharSequence?) {}
    override fun swipeDown() {}
    override fun swipeLeft() {}
    override fun swipeRight() {}
    override fun swipeUp() {}
}