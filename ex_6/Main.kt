
package com.example.ex_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.ex_1.ui.theme.Ex_1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Ex_1Theme(    // uses dynamic color + dark theme from Theme.kt
                // darkTheme and dynamicColor use their defaults (system settings)
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CountryListScreen()
                }
            }
        }
    }
}

@Composable
fun CountryListScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CountryCard(
            countryName = "Japan",
            flag = "🇯🇵",
            capital = "Tokyo"
        )
        CountryCard(
            countryName = "Canada",
            flag = "🇨🇦",
            capital = "Ottawa"
        )
        CountryCard(
            countryName = "Brazil",
            flag = "🇧🇷",
            capital = "Brasília"
        )
        CountryCard(
            countryName = "Germany",
            flag = "🇩🇪",
            capital = "Berlin"
        )
    }
}

@Composable
fun CountryCard(
    countryName: String,
    flag: String,
    capital: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp), // custom card shape
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = flag,
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = countryName,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Capital: $capital",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CountryListPreview() {
    Ex_1Theme {
        CountryListScreen()
    }
}
