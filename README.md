<p align="center">
  <img
    alt="erzaehler-logo"
    src="https://github.com/user-attachments/assets/d778b280-5e7d-4d7c-9f2f-19756444d8e9"
    width="800"
  />
</p>

> This project was created for the [Kotlin Multiplatform Contest](https://kotlinconf.com/contest/).

erzaehler is a small visual‑novel generator to help you practice a language. Provide a topic (or let the app suggest one), choose a language and level, and watch a short scene play out with generated voiceover. Each story is created on the fly, so every run is a little different.

Built with Kotlin Multiplatform and Compose Multiplatform, it runs on Android and iOS with a small Ktor backend that manages story generation and text‑to‑speech.

<br>

> Unmute the videos.

https://github.com/user-attachments/assets/0b66aca8-ad54-4aff-b8b6-ba1f5dd6c494

https://github.com/user-attachments/assets/9b0c3339-456d-4896-866d-ef6f90e9b0d1


## Setup

The project has three modules:
- composeApp: Compose Multiplatform UI and DI (Koin); platform bindings for audio
 - shared: domain models, use cases, and repositories
- server: Ktor backend exposing `/topic`, `/story`, `/voice`; including a small wrapper for the Cartesia API

You’ll run the server once, then start any client target you want.

### Prerequisites

- macOS (required for iOS; optional for Android)
- Android Studio and Xcode
- JDK 11+ (project targets Java 11)
- Gradle (wrapper included: `./gradlew`)

Helpful references:
- [Kotlin Multiplatform setup](https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-create-first-app)
- [Xcode](https://developer.apple.com/xcode/)
- [Android Studio](https://developer.android.com/studio)

### Backend setup (API keys)

The server uses:
- Google Gemini for topic/story generation
- Cartesia for text‑to‑speech

Create API keys and export them as environment variables before launching the server:

```bash
export GOOGLE_API_KEY=YOUR_GEMINI_KEY
export CARTESIA_API_KEY=YOUR_CARTESIA_KEY
```

Then run the backend:

> Or open the project and use the `Run ApplicationKt` button in your IDE.
```bash
./gradlew :server:run
```

The server starts on http://127.0.0.1:8080.

Notes:
- iOS simulator and Desktop use 127.0.0.1 directly.
- Android emulator uses a separate loopback. Easiest fix: reverse port 8080 so 127.0.0.1 works in the app:

```bash
adb reverse tcp/8080 tcp/8080
```

If you cannot use `adb reverse`, change the client host in `shared/src/commonMain/kotlin/com/karastift/erzaehler/Constants.kt` (`SERVER_HOST`) to `10.0.2.2` for the Android emulator.

### Mobile app setup

I recommend to follow the [official documentation](https://kotlinlang.org/docs/multiplatform/multiplatform-create-first-app.html#run-your-application) for running the actual mobile app either on Android or iOS Simulator.

### Character assets (license & setup)

This project uses character sprites from: https://pixelserial.itch.io/rpg-top-down-character-asset-pack

License summary (author’s terms):
- You can: use in personal/commercial projects; modify and include in games (free or paid).
- You can’t: resell or redistribute the assets (even if modified).

Because redistribution is forbidden, the sprites are not included in this repository. To use them locally:

1) Download the asset pack and extract it.
2) Slice the sprites into frames using the helper script [slice-assets.py](slice-assets.py):
3) Copy the generated frames into Compose resources so the app can load them:

## App flow

1) Choose Language and Level which affects story and speech speed.
2) Enter a topic or tap the sparkle icon to generate one.
3) Tap the submit button and listen!

## Built With

1. [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) — shared domain + networking across targets
2. [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) — declarative UI for Android, iOS, Desktop, Web
3. [Ktor](https://ktor.io/) — backend server and HTTP client
4. [Koin](https://insert-koin.io/) — lightweight dependency injection
5. [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) — JSON serialization
6. [Koog (LLM)](https://docs.koog.ai/) — prompt execution for topic/story generation
7. [Cartesia TTS](https://cartesia.ai/sonic) — voice generation for dialogue

```bash
mkdir -p composeApp/src/commonMain/composeResources/drawable
cp presliced_flattened/*.png composeApp/src/commonMain/composeResources/drawable/
```

## Troubleshooting

- 401/403 from backend: verify `GOOGLE_API_KEY` and `CARTESIA_API_KEY` are set in your shell.
- Android cannot reach backend: run `adb reverse tcp/8080 tcp/8080` (or change `SERVER_HOST` to `10.0.2.2`).

## License

This project is licensed under the MIT License. See [LICENSE](LICENSE).
