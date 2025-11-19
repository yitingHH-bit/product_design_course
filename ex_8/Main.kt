
package com.example.ex_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ex_1.ui.theme.Ex_1Theme
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// -----------------------
// Retrofit & data models
// -----------------------

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
// TODO: replace with your real API key from OpenWeather
private const val API_KEY = "bd5e378503939ddaee76f12ad7a97608"


data class WeatherMain(
    val temp: Double,
    val humidity: Int
)

data class WeatherResponse(
    val name: String,
    val main: WeatherMain
)

interface WeatherApi {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse
}

object RetrofitInstance {
    val api: WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }
}

// -----------------------
// ViewModel and UI state
// -----------------------

data class CityWeatherState(
    val city: String,
    val temperature: Double? = null,
    val humidity: Int? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class WeatherViewModel : ViewModel() {

    // You can change this list to any cities you like
    private val cities = listOf(
        "Helsinki",
        "London",
        "Tokyo",
        "New York",
        "Sydney"
    )

    val weatherStates = mutableStateListOf<CityWeatherState>()

    init {
        // Initialize list with loading states
        cities.forEach { city ->
            weatherStates.add(CityWeatherState(city = city, isLoading = true))
            fetchWeatherForCity(city)
        }
    }

    private fun fetchWeatherForCity(city: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getWeather(
                    city = city,
                    apiKey = API_KEY
                )
                val index = weatherStates.indexOfFirst { it.city == city }
                if (index != -1) {
                    weatherStates[index] = CityWeatherState(
                        city = response.name,
                        temperature = response.main.temp,
                        humidity = response.main.humidity,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                val index = weatherStates.indexOfFirst { it.city == city }
                if (index != -1) {
                    weatherStates[index] = CityWeatherState(
                        city = city,
                        isLoading = false,
                        error = e.message ?: "Unknown error"
                    )
                }
            }
        }
    }
}

// -----------------------
// Activity & Composables
// -----------------------

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Ex_1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherScreen()
                }
            }
        }
    }
}

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {
    val items = viewModel.weatherStates

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "City Weather",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items) { state ->
                WeatherCard(state = state)
            }
        }
    }
}

@Composable
fun WeatherCard(state: CityWeatherState) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = state.city,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            when {
                state.isLoading -> {
                    Text(text = "Loading...", style = MaterialTheme.typography.bodyMedium)
                }

                state.error != null -> {
                    Text(
                        text = "Error: ${state.error}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                else -> {
                    Text(
                        text = "Temperature: ${state.temperature} °C",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Humidity: ${state.humidity} %",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WeatherPreview() {
    Ex_1Theme {
        // simple preview with dummy data
        val dummy = remember {
            listOf(
                CityWeatherState("Helsinki", 5.2, 80, isLoading = false),
                CityWeatherState("Tokyo", 18.3, 60, isLoading = false)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("City Weather", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(16.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(dummy) { WeatherCard(it) }
            }
        }
    }
}
