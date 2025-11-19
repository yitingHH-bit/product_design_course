
package com.example.ex_1

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

// DataStore keys and instance
val Context.dataStore by preferencesDataStore(name = "user_prefs")
val FONT_SIZE_KEY = intPreferencesKey("font_size")
val FONT_STYLE_KEY = stringPreferencesKey("font_style")

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SensorApp()
        }
    }
}

@Composable
fun SensorApp() {
    val context = LocalContext.current
    val sensorManager = remember { context.getSystemService(SensorManager::class.java) }
    val coroutineScope = rememberCoroutineScope()

    // Sensor display strings
    var magnetometerData by remember { mutableStateOf("Magnetometer: N/A") }
    var temperatureData by remember { mutableStateOf("Temperature: N/A") }
    var luxData by remember { mutableStateOf("Light (lux): N/A") }

    // Font size from DataStore
    val fontSizeFlow = context.dataStore.data.map { it[FONT_SIZE_KEY] ?: 16 }
    val fontSize by produceState(initialValue = 16, key1 = fontSizeFlow) {
        value = fontSizeFlow.first()
    }

    // Font style from DataStore (Default / Bold / Italic)
    val fontStyleFlow = context.dataStore.data.map { it[FONT_STYLE_KEY] ?: "Default" }
    var fontStyle by remember { mutableStateOf("Default") }

    LaunchedEffect(fontStyleFlow) {
        fontStyle = fontStyleFlow.first()
    }

    val fontWeight = when (fontStyle) {
        "Bold" -> FontWeight.Bold
        else -> FontWeight.Normal
    }
    val fontStyleCompose = when (fontStyle) {
        "Italic" -> FontStyle.Italic
        else -> FontStyle.Normal
    }

    // Sensor listener
    val sensorListener = remember {
        object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    when (it.sensor.type) {
                        Sensor.TYPE_MAGNETIC_FIELD -> {
                            magnetometerData = "Magnetometer: x=%.2f, y=%.2f, z=%.2f".format(
                                it.values[0],
                                it.values[1],
                                it.values[2]
                            )
                        }
                        Sensor.TYPE_AMBIENT_TEMPERATURE -> {
                            temperatureData = "Temperature: %.2f °C".format(it.values[0])
                        }
                        Sensor.TYPE_LIGHT -> {
                            luxData = "Light: %.2f lx".format(it.values[0])
                        }
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }
    }

    // Register sensors
    DisposableEffect(sensorManager) {
        val magSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        val tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        val lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        magSensor?.let {
            sensorManager.registerListener(sensorListener, it, SensorManager.SENSOR_DELAY_UI)
        }
        tempSensor?.let {
            sensorManager.registerListener(sensorListener, it, SensorManager.SENSOR_DELAY_UI)
        }
        lightSensor?.let {
            sensorManager.registerListener(sensorListener, it, SensorManager.SENSOR_DELAY_UI)
        }

        onDispose {
            sensorManager.unregisterListener(sensorListener)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Sensor readings
        Text(
            text = magnetometerData,
            fontSize = fontSize.sp,
            fontWeight = fontWeight,
            fontStyle = fontStyleCompose,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = temperatureData,
            fontSize = fontSize.sp,
            fontWeight = fontWeight,
            fontStyle = fontStyleCompose,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = luxData,
            fontSize = fontSize.sp,
            fontWeight = fontWeight,
            fontStyle = fontStyleCompose,
            modifier = Modifier.padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Font style selection (Default / Bold / Italic)
        Text(
            text = "Select Font Style:",
            fontSize = 18.sp,
            modifier = Modifier.padding(8.dp)
        )

        val fontStyles = listOf("Default", "Bold", "Italic")
        fontStyles.forEach { style ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                RadioButton(
                    selected = (style == fontStyle),
                    onClick = {
                        fontStyle = style
                        coroutineScope.launch {
                            context.dataStore.edit { settings ->
                                settings[FONT_STYLE_KEY] = style
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = style, fontSize = 16.sp)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Optional: keep font size toggle (16 <-> 24)
        Button(
            onClick = {
                coroutineScope.launch {
                    context.dataStore.edit { settings ->
                        settings[FONT_SIZE_KEY] = if (fontSize == 16) 24 else 16
                    }
                }
            }
        ) {
            Text("Toggle Font Size")
        }
    }
}
