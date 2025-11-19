package com.example.ex_1.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp

// "Geo" font family placeholder – uses system sans-serif.
// No external font file required.
val GeoFontFamily = FontFamily.SansSerif

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = GeoFontFamily,
        fontSize = 22.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = GeoFontFamily,
        fontSize = 16.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = GeoFontFamily,
        fontSize = 30.sp
    )
    // add more text styles if needed
)
