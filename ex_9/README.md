
<img width="1137" height="638" alt="image" src="https://github.com/user-attachments/assets/1b020f16-f921-4c07-8205-cfd07109cec9" />
# Shopping List Room Database App

This Android app is built using Kotlin 2.0.21, Jetpack Compose, and Room.
It stores shopping items (name, quantity, unit, price) in a local SQLite database.

## Features
- Add shopping items via text fields
- Store data in Room database
- Display items in a table using LazyColumn
- Delete individual items
- Database Inspector support for viewing stored data

## Tech Stack
- Kotlin 2.0.21
- Jetpack Compose
- Room (Runtime, KTX, Compiler)
- ViewModel + Coroutines

## How to Run
1. Create Android Studio project (API 35, Compose).
2. Add `ksp` plugin and Room dependencies to `build.gradle.kts`.
3. Replace `MainActivity.kt` with the code from this repo.
4. Run the app and add items.
5. Open **Database Inspector** to view `shopping-db`.
