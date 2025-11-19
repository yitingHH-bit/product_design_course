In the Android view :

Expand Gradle Scripts

Double-click build.gradle.kts (Module: app)
(the one that literally says “Module: app” in brackets)

<img width="1172" height="542" alt="image" src="https://github.com/user-attachments/assets/7bbd8e5d-de75-4135-b136-943cd764b9a1" />

implementation("com.squareup.retrofit2:retrofit:2.9.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
# Weather LazyList App

This is an Android app written in Kotlin using Jetpack Compose.  
It fetches weather data from the OpenWeather API and shows it in a scrollable list.

## Features

- Uses **Retrofit** + **Gson** to call the OpenWeather REST API
- Displays **temperature** and **humidity** for several cities
- Shows each city in a card inside a **LazyColumn**
- Basic loading and error states using a **ViewModel** with coroutines

## How to Run

1. Create a free account at [OpenWeather](https://openweathermap.org/) and get an API key.
2. Open the project in Android Studio.
3. In `Main.kt`, replace:

   ```kotlin
   private const val API_KEY = "YOUR_API_KEY_HERE"
