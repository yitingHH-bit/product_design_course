
package com.example.ex_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationApp()
                }
            }
        }
    }
}

@Composable
fun NavigationApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        // 1) Home screen – enter name
        composable("home") {
            HomeScreen(
                onNext = { name ->
                    // pass name to second screen
                    navController.navigate("numbers/$name")
                }
            )
        }

        // 2) Second screen – enter two numbers
        composable(
            route = "numbers/{name}",
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""

            NumberScreen(
                name = name,
                onNext = { n1, n2 ->
                    navController.navigate("result/$name/$n1/$n2")
                },
                onBack = {
                    navController.popBackStack()   // back to home
                }
            )
        }

        // 3) Third screen – show name + sum
        composable(
            route = "result/{name}/{n1}/{n2}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("n1") { type = NavType.StringType },
                navArgument("n2") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val n1Str = backStackEntry.arguments?.getString("n1") ?: "0"
            val n2Str = backStackEntry.arguments?.getString("n2") ?: "0"

            val n1 = n1Str.toIntOrNull() ?: 0
            val n2 = n2Str.toIntOrNull() ?: 0
            val sum = n1 + n2

            ResultScreen(
                name = name,
                sum = sum,
                onGoHome = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = false }
                        launchSingleTop = true
                    }
                }
                // device/system back button will automatically return to screen 2
            )
        }
    }
}

// ----------------- Screen 1: Home -----------------

@Composable
fun HomeScreen(
    onNext: (String) -> Unit
) {
    var name by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Home Screen", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        Text("Enter your name:")
        Spacer(Modifier.height(8.dp))

        TextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = { onNext(name) },
            enabled = name.isNotBlank()
        ) {
            Text("Next")
        }
    }
}

// ----------------- Screen 2: Numbers -----------------

@Composable
fun NumberScreen(
    name: String,
    onNext: (String, String) -> Unit,
    onBack: () -> Unit
) {
    var number1 by remember { mutableStateOf("") }
    var number2 by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Second Screen", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(8.dp))
        Text("Hi, $name! Enter two numbers:")

        Spacer(Modifier.height(16.dp))

        TextField(
            value = number1,
            onValueChange = { number1 = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Number 1") }
        )
        Spacer(Modifier.height(8.dp))
        TextField(
            value = number2,
            onValueChange = { number2 = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Number 2") }
        )

        Spacer(Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = onBack) {
                Text("Back")
            }
            Button(
                onClick = { onNext(number1, number2) },
                enabled = number1.isNotBlank() && number2.isNotBlank()
            ) {
                Text("Next")
            }
        }
    }
}

// ----------------- Screen 3: Result -----------------

@Composable
fun ResultScreen(
    name: String,
    sum: Int,
    onGoHome: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Third Screen", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))

        Text("Hello, $name!")
        Spacer(Modifier.height(8.dp))
        Text("The sum of your two numbers is:")
        Spacer(Modifier.height(4.dp))
        Text(
            text = sum.toString(),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(24.dp))

        Button(onClick = onGoHome) {
            Text("Go Home")
        }
        // System back button will automatically navigate back to the number screen
    }
}
