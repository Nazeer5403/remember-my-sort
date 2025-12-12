# Remember My Sort

Restores sort persistence to the native Android file picker via LSPosed.

![Android API](https://img.shields.io/badge/API-31%2B-brightgreen)
![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-blue)

---

## Why This Exists

Android's native file picker ([DocumentsUI](https://developer.android.com/guide/topics/providers/document-provider)) fails to persist user sort preferences. Every time it launches, it resets to alphabetical order, forcing users to manually re-sort their files during every single session.

This affects all applications using the [Storage Access Framework](https://developer.android.com/guide/topics/providers/document-provider) on Android 12+. When apps invoke the system file picker, it initializes the sort state as `UNKNOWN`, [forcing alphabetical order](https://android.googlesource.com/platform/frameworks/base/+/98d7c7a/packages/DocumentsUI/src/com/android/documentsui/DirectoryLoader.java) regardless of previous selection. The regression appeared in [Android 12](https://source.android.com/docs/core/ota/modular-system/documentsui), dropping the per-directory persistence [introduced in Android 4.4](https://android.googlesource.com/platform/frameworks/base/+/d182bb6). Users have been [reporting this issue since 2021](https://xdaforums.com/t/google-files-default-sort.4309799/), yet an official fix has not been implemented.

---

## How it Works

Hooks into DocumentsUI sort logic. Manual sort changes are persisted to storage and restored on subsequent picker launches. Defaults to date descending on first run.

---

## Requirements

- LSPosed framework (API 100)
- Android 12+ (API 31+)

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
