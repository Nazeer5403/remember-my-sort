# Remember My Sort

Fixes the persistent sort reset issue in Android 11+ system file picker via LSPosed.

![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-blue)
![Android API](https://img.shields.io/badge/API-30%2B-brightgreen)


---

## Why This Exists

Starting with [Android 11](https://developer.android.com/about/versions/11/privacy/storage#scoped-storage), all apps that need file access must use the system file picker. This picker fails to persist sort preferences across directories. Sort order is kept in the root view, but navigating into any subdirectory resets it to filename A-Z. Users must manually change the sort every time they browse into a folder, regardless of how many times they've set it before.

Users have [reported the issue since 2021](https://xdaforums.com/t/google-files-default-sort.4309799/) with no fix from Google.

---

## How it Works

Hooks into DocumentsUI sort logic. Manual sort changes are persisted to storage and restored on subsequent picker launches. Defaults to date descending on first run.

---

## Requirements

- LSPosed framework (API 100)
- Android 11+ (API 30+)

---

## Compatibility

Works on AOSP-based ROMs and Pixel devices. OEM-modified ROMs are untested.

---

## Installation

1. Install LSPosed framework
2. Download and install the APK from [Releases](../../releases)
3. Enable the module in LSPosed Manager
4. Set scope to `com.google.android.documentsui`
5. Reboot or force stop DocumentsUI via `Settings > Apps > Files`

---

## Build

```bash
./gradlew assembleDebug
```

Requires JDK 21 and Gradle 8.13.

---

## License

[MIT](LICENSE)
