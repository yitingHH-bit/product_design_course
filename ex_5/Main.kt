
package com.example.ex_1   // <-- IMPORTANT: match the folder & manifest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThreeCountersApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CounterRow(
    modifier: Modifier = Modifier
) {
    var textValue by remember { mutableStateOf("0") }

    fun currentInt(): Int = textValue.toIntOrNull() ?: 0

    Row(
        modifier = modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = {
                val newValue = currentInt() - 1
                textValue = newValue.toString()
            }
        ) {
            Text("-")
        }

        Spacer(modifier = Modifier.width(8.dp))

        TextField(
            value = textValue,
            onValueChange = { newText ->
                if (newText.isEmpty() || newText.toIntOrNull() != null) {
                    textValue = newText
                }
            },
            modifier = Modifier.width(120.dp),
            singleLine = true,
            label = { Text("Start value") }
        )

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = {
                val newValue = currentInt() + 1
                textValue = newValue.toString()
            }
        ) {
            Text("+")
        }
    }
}

@Composable
fun ThreeCountersApp() {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Three Counters",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(16.dp))

                CounterRow()
                CounterRow()
                CounterRow()
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ThreeCountersAppPreview() {
    ThreeCountersApp()
}
