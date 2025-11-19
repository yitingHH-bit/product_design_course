Go through the following codelabs for material design and changing app icon.
Create an app for displaying the name and flag of 4 different countries of your choice in cards. (Use Column to stack the cards and NOT LazyColumn)
Enable dynamicColor and darkTheme in the ui.theme/Theme.kt file.
Customize the background color and shape of the cards as you wish. Use the font Geo for the texts in your card.
Change the icon of the app to an image of the globe.


# Country Cards App

This is an Android app written in Kotlin using Jetpack Compose.  
It displays four different countries in Material Design cards.

## Features

- Shows the **name and flag** (emoji) of 4 countries in cards
- Cards are stacked in a **Column** (no LazyColumn)
- Custom card background color, shape, and elevation
- Uses the **Geo** font for all text
- **Dynamic color** and **dark theme** enabled via Material 3
- App icon changed to a **globe** image

## How to Run

1. Open the project in Android Studio.
2. Make sure you have:
   - `geo_regular.ttf` in `app/src/main/res/font/`
3. Connect an emulator or device.
4. Click **Run ▶** to launch the app.

The main UI is implemented in `Main.kt` (`MainActivity`) and themed using files in `ui.theme`.

