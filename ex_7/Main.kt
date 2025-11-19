
# Multi Counter LazyColumn App

This is an Android app written in Kotlin using Jetpack Compose.  
It shows a scrollable list of independent counters.

## Features

- Scrollable list of counters in a **LazyColumn**
- Each counter row is labelled `Counter_#`
- Each item has:
  - A **“-”** button to decrement
  - A **displayed value**
  - A **“+”** button to increment
- Every counter value is independent
- Optional feature:
  - **Add counter** button to append a new counter at runtime
  - **Remove last** button to delete the last counter

## How to Run

1. Open the project in Android Studio.
2. Make sure `Main.kt` (or `MainActivity.kt`) contains the multi-counter code.
3. Run the `app` module on an emulator or device.
4. You will see a scrollable list of counters with names `Counter_1`, `Counter_2`, etc.

The main UI logic is implemented in `Main.kt`.
