# AI Keyboard - Complete Setup & Features Guide

## 🎯 Quick Start

### 1. **Adding Your API Key**

To use the AI text rewriting features, you need a Cerebras API key:

1. Go to [Cerebras.ai](https://www.cerebras.ai) and sign up
2. Generate an API key from your account settings
3. Open the AI Keyboard app on your phone
4. Tap "Open Settings"
5. Paste your API key in the "Cerebras API Key" field
6. Select your preferred tone (Formal, Casual, Professional)
7. Tap "Save Settings"

### 2. **Enabling the Keyboard**

1. Go to **Settings > Language & Input > Keyboards** (varies by Android version)
2. Find "AI Keyboard" in the list
3. Enable it
4. Set it as your default keyboard (when prompted or in Input Method settings)

### 3. **Using the Keyboard**

- **Type normally**: Regular typing works as expected with auto-correction
- **AI Rewrite**: Tap the "AI" button to rewrite selected text with your chosen tone
- **Cycle Tones**: Long-press the "AI" button to cycle through tones (Formal → Casual → Professional)

---

## 🎨 Features

### **Themes (5 options)**

Choose your preferred keyboard appearance:

1. **Light Theme** - Clean white background with light gray keys
2. **Dark Theme** - Dark background perfect for night usage
3. **Google Light** - Google Keyboard inspired light theme with blue accents
4. **Google Dark** - Google Keyboard inspired dark theme
5. **Material You** - Modern Material Design 3 style with purple accents

**How to change theme:**
- Open Settings → Select "Theme" dropdown → Choose theme → Save

### **Auto Text Correction**

The keyboard automatically fixes common spelling mistakes:

- "teh" → "the"
- "recieve" → "receive"
- "occured" → "occurred"
- "adress" → "address"
- "seperate" → "separate"
- "thier" → "their"
- ...and many more!

**Toggle auto-correction:**
- Settings → Enable/Disable "Auto Correction" checkbox

### **Sound & Vibration**

Get tactile and audio feedback while typing:

- **Key Sound**: Soft click sound on each key press
- **Vibration**: Short haptic feedback for responsive typing

**To customize:**
- Settings → Toggle "Enable Key Sound" and "Enable Vibration"
- These provide instant feedback like Google Keyboard

### **Google Keyboard-like UI**

The keyboard features:

- Modern Material Design styling
- Smooth, rounded key buttons
- Shadow effects for depth
- Clean, minimalist layout
- Responsive key press animations
- Optimized spacing for easy typing

### **Text Rewriting Tones**

Three AI-powered styles for any message:

1. **Formal**: Professional, structured writing
   - Example: "yo whats up" → "Hello, how are you?"

2. **Casual**: Friendly, conversational tone
   - Example: "I require assistance" → "Hey, can you help me?"

3. **Professional**: Business-appropriate language
   - Example: "nope cant do it" → "Unfortunately, I'm unable to complete this task"

### **Personalization Options**

Customize the keyboard to your needs:

- **API Key**: Set your Cerebras API key for AI features
- **Default Tone**: Choose which tone to use by default
- **Theme**: 5 different visual themes
- **Sound**: Enable/disable key click sound
- **Vibration**: Enable/disable haptic feedback
- **Auto Correction**: Enable/disable spelling corrections

---

## ⚙️ Settings Breakdown

| Setting | Options | Default | Purpose |
|---------|---------|---------|---------|
| Cerebras API Key | (Your key) | Empty | Enable AI text rewriting |
| Default Tone | Formal, Casual, Professional | Formal | Default AI rewrite style |
| Theme | 5 themes | Light | Keyboard appearance |
| Key Sound | On/Off | On | Audio feedback while typing |
| Vibration | On/Off | On | Haptic feedback while typing |
| Auto Correction | On/Off | On | Automatic spelling fixes |

---

## 🚀 Tips for Best Experience

1. **API Key**: Get your free Cerebras API key at [cerebras.ai](https://www.cerebras.ai)
2. **Themes**: Switch themes based on lighting conditions or personal preference
3. **Sound**: Disable sound in quiet environments
4. **Vibration**: Keep vibration on for better typing feedback
5. **Auto Correction**: Enable for faster typing, disable if you need to type code/special text

---

## 🔧 Technical Details

### Architecture

- **ThemeManager**: Centralized theme management with 5 pre-built themes
- **AutoCorrection**: Dictionary-based spelling correction engine
- **SoundVibrationManager**: Unified feedback (sound + vibration) control
- **AIKeyboardService**: Main IME service with API integration
- **SettingsActivity**: User preferences management

### Permissions

The app requires:
- `INTERNET` - For API calls to Cerebras
- `ACCESS_NETWORK_STATE` - To check internet connectivity
- `VIBRATE` - For haptic feedback
- `BIND_INPUT_METHOD` - To function as an IME

### External Dependencies

- **OkHttp3**: HTTP client for API requests
- **Kotlin Coroutines**: Async API calls
- **Cerebras API**: For AI text rewriting

---

## 🐛 Troubleshooting

### Keyboard not appearing?
- Go to Settings > Languages & Input > Keyboards
- Make sure AI Keyboard is enabled
- Set it as your default input method

### AI features not working?
- Verify your API key is correct
- Check internet connection
- Make sure API key is from Cerebras.ai, not OpenAI

### No sound/vibration?
- Open Settings and enable "Key Sound" and "Vibration"
- Check phone volume settings (keyboard uses system sounds)
- Verify permissions are granted

### Auto-correction not working?
- Open Settings and enable "Auto Correction"
- Only works for common misspellings in the dictionary

---

## 📱 System Requirements

- **Minimum Android**: API 24 (Android 7.0)
- **Target Android**: API 34 (Android 14)
- **RAM**: 50MB minimum
- **Storage**: 10MB

---

## 🔐 Privacy Note

- Your API key is stored locally on your device
- Keyboard data is not logged or sent anywhere except to Cerebras API for rewriting
- All settings are stored in SharedPreferences (local)

---

## 📝 Version History

### v1.0 (Latest)
- ✅ API key management
- ✅ AI text rewriting (3 tones)
- ✅ 5 themes
- ✅ Auto text correction
- ✅ Sound & vibration feedback
- ✅ Google Keyboard-like UI
- ✅ Settings customization

---

**Made with ❤️ by AI Developer**

For more info: Visit [Cerebras.ai](https://www.cerebras.ai)
