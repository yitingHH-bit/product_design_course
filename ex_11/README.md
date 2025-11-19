<img width="1121" height="613" alt="image" src="https://github.com/user-attachments/assets/6056bb12-6f62-46d3-9537-09633785a518" />
# Three Screen Navigation App

This Android app is written in Kotlin using Jetpack Compose and Navigation Compose.  
It demonstrates basic multi-screen navigation and passing data between screens.

## Features

- **Home Screen**
  - Text field to enter your name.
  - “Next” button navigates to the second screen.

- **Second Screen**
  - Two text fields to enter numbers.
  - “Next” button navigates to the third screen.
  - “Back” button returns to the home screen.

- **Third Screen**
  - Displays the entered name.
  - Calculates and displays the **sum** of the two numbers.
  - “Go Home” button navigates directly back to the home screen.
  - System back button returns to the second screen.

## Tech Stack

- Kotlin
- Jetpack Compose
- Navigation Compose  
- (Optional) Accompanist Navigation Animation

## How to Run

1. Open the project in Android Studio.
2. Make sure the following dependencies are in `build.gradle.kts` (Module: `app`):

   ```kotlin
   implementation("androidx.navigation:navigation-compose:2.7.7")
   implementation("com.google.accompanist:accompanist-navigation-animation:0.33.2-alpha")
