# Remember My Sort

LSPosed module that sets DocumentsUI file picker default sort to date descending on Android 12+.

![Android API](https://img.shields.io/badge/API-31%2B-brightgreen)
![Kotlin](https://img.shields.io/badge/Kotlin-2.0.0-blue)

## Problem

Android file picker resets to alphabetical sort every time. Pick "sort by date", close the picker, open again, back to A-Z.

Affects every app using the file picker (WhatsApp, Telegram, Gmail, browsers). Been broken [since 2021](https://xdaforums.com/t/google-files-default-sort.4309799/). Affects Pixel, Samsung, Xiaomi.

Why this sucks: Downloads folder has newest files at the bottom when sorted alphabetically. Downloaded something 5 minutes ago? Scroll past 300 old files to find it.

**Google response:** None. Broken for 4+ years.

## Solution

Sets default to date descending. Respects manual sort if you pick one.

## Requirements

- LSPosed framework installed
- Android 12 or newer

Tested on Android 13-16 QPR2. Target package: `com.google.android.documentsui`

## Installation

1. Install [LSPosed](https://github.com/JingMatrix/LSPosed)
2. Install module APK
3. Enable module in LSPosed Manager
4. Add `com.google.android.documentsui` to scope
5. Force stop DocumentsUI or reboot

## Build

```bash
./gradlew assembleDebug
```

Requirements: JDK 21, Gradle 8.10+

## How it works

Hooks DocumentsUI sort logic. Applies date descending when you haven't picked a sort. Detects which field is the date field instead of assuming fixed values.

## License

[MIT](LICENSE)
