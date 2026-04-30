# AI Keyboard App

An Android keyboard app that provides AI-powered grammar correction and tone adjustment using the Cerebras API.

## Features

- Standard QWERTY keyboard layout (Google Keyboard style)
- AI button for grammar correction and tone adjustment
- Tone selection (Formal, Casual, Professional) via long press
- Response caching for faster performance
- Battery-efficient design with proper threading
- Settings screen for API key and preferences
- Production-ready with ProGuard and signing config

## Setup

1. Clone the repository
2. Open in Android Studio or VS Code
3. Get your Cerebras API key from [Cerebras.ai](https://cerebras.ai)
4. Run the app and go to Settings to enter your API key
5. Enable the keyboard in Android system settings

## API Key Setup

**Important:** Add your Cerebras API key in the app settings, not in code. The app securely stores it in SharedPreferences.

1. Launch the app
2. Tap "Open Settings"
3. Enter your Cerebras API key
4. Select default tone
5. Save settings

## Usage

1. Enable AI Keyboard in Android Settings > System > Languages & input > Virtual keyboard > Manage keyboards
2. Select AI Keyboard as your input method
3. Type text in any app
4. Tap the "AI" button to rewrite with correct grammar in selected tone
5. Long press the "AI" button to cycle through tones (Formal → Casual → Professional)

## Production Build

To build a release APK locally:

1. Create a keystore: `keytool -genkey -v -keystore keystore.jks -keyalg RSA -keysize 2048 -validity 10000 -alias key_alias`
2. Update `app/build.gradle` with your keystore details
3. Run `./gradlew assembleRelease`

## GitHub Actions Build

A CI workflow is included at `.github/workflows/android-build.yml`.
When code is pushed to `main`, GitHub Actions will:

- install JDK 17
- install Android SDK tools, build-tools 34, and platform 34
- run `./gradlew assembleRelease`
- upload the release APK as a workflow artifact

This means your repo will build a release APK automatically and make it available for sharing.

## Architecture

- **AIKeyboardService**: Main IME service handling keyboard input and AI processing
- **SettingsActivity**: Configuration screen for API key and preferences
- **Caching**: In-memory cache for API responses to reduce network calls
- **Threading**: Coroutines for async API calls without blocking UI
- **Error Handling**: Graceful failure with user notifications

## API Integration

The app uses Cerebras API with the following prompt structure:
```
Rewrite the following text with correct grammar and punctuation in a [tone] tone. Only return the rewritten text, no explanations: [user_text]
```

Supported tones: formal, casual, professional

## Security

- API key stored securely in SharedPreferences (not hardcoded)
- Network requests use HTTPS
- ProGuard obfuscation in release builds
- No sensitive data logging

## Requirements

- Android API 24+ (Android 7.0)
- Internet permission for API calls
- Cerebras API key

## Troubleshooting

- **"API key not set"**: Enter your Cerebras API key in app settings
- **AI rewrite fails**: Check internet connection and API key validity
- **Keyboard not appearing**: Enable AI Keyboard in system settings