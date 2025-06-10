# Subsonic API (Kotlin Multiplatform)

[![Maven Central](https://img.shields.io/maven-central/v/ru.stersh/subsonic-api.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22ru.stersh%22%20AND%20a:%22subsonic-api%22)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://mit-license.org)

This is a Kotlin Multiplatform library that provides a convenient and idiomatic way to interact with the Subsonic API. It's designed to be used in various Kotlin projects, including Android, iOS, JVM, and Linux applications.

## Features

*   **Kotlin Multiplatform:** Supports Android, iOS (x64, Arm64, SimulatorArm64), JVM, and Linux (x64).
*   **Coroutines-Based:** Leverages Kotlin coroutines for asynchronous network operations, making it easy to handle API calls in a non-blocking way.
*   **Ktor Client:** Uses the powerful Ktor HTTP client for making network requests.
*   **Serialization:** Uses kotlinx.serialization for efficient and type-safe JSON handling.
*   **Easy to Use:** Provides a clean and intuitive API for common Subsonic operations.

## Installation

### Gradle (Kotlin DSL)

Add the following dependency to your module's `build.gradle.kts` file:
```
dependencies { implementation("ru.stersh:subsonic-api:1.1.2")  }
```

## Platforms

This library supports the following platforms:

*   **Android**
*   **iOS** (x64, Arm64, SimulatorArm64)
*   **JVM**
*   **Linux** (x64)

## Dependencies

This library relies on the following key dependencies:

*   [Ktor Client](https://github.com/ktorio/ktor)
*   [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)
*   [hash](https://github.com/KotlinCrypto/hash)

## Contributing

Contributions are welcome! If you find a bug or have a feature request, please open an issue or submit a pull request or [issue](https://github.com/siper/subsonic-api/issues).

## License

This project is licensed under the MIT License - see the [LICENSE](https://mit-license.org) file for details.
