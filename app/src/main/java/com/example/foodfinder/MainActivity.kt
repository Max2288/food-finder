package com.example.foodfinder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.NavHost
import com.example.foodfinder.ui.theme.FoodFinderTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodFinderApp()
        }
    }
}

@Composable
fun FoodFinderApp() {
    FoodFinderTheme {
        val navController = rememberNavController()
        NavHost(navController, startDestination = "login") {
            composable("login") { LoginScreen(navController) }
            composable("scanner") { BarcodeScannerScreen() }
            composable("info") { InfoScreen() }
        }
    }
}



@Composable
fun BarcodeScannerScreen() {
    // Здесь будет реализована логика сканирования QR-кодов
}

@Composable
fun InfoScreen() {
    // Здесь будет отображаться информация о продукте
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    FoodFinderTheme {
        val navController = rememberNavController()
        LoginScreen(navController)
    }
}
