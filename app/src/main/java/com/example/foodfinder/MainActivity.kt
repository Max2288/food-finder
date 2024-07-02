package com.example.foodfinder

import DataStoreManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.foodfinder.model.InfoParamType
import com.example.foodfinder.model.Product
import com.example.foodfinder.screens.BarcodeScannerScreen
import com.example.foodfinder.screens.HistoryScreen
import com.example.foodfinder.screens.InfoScreen
import com.example.foodfinder.screens.LoginScreen
import com.example.foodfinder.screens.MainMenu
import com.example.foodfinder.ui.theme.FoodFinderTheme
import com.example.foodfinder.ui.theme.ThemeManager

class MainActivity : ComponentActivity() {
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStoreManager = DataStoreManager(this)

        setContent {
            FoodFinderApp(dataStoreManager)
        }
    }
}

@Composable
fun FoodFinderApp(dataStoreManager: DataStoreManager) {
    val isDarkTheme by ThemeManager.isDarkTheme
    FoodFinderTheme(darkTheme = isDarkTheme) {
        val navController = rememberNavController()
        NavHost(navController, startDestination = "login") {
            composable("login") { LoginScreen(navController) }
            composable("scanner") { BarcodeScannerScreen(navController, dataStoreManager) }
            composable("mainMenu") {
                MainMenu(navController = navController, dataStoreManager = dataStoreManager)
            }
            composable(
                "info/{product}",
                arguments = listOf(navArgument("product") { type = InfoParamType() }),
            ) {
                val product = it.arguments?.getParcelable<Product>("product")
                InfoScreen(navController, product)
            }
            composable("history") { HistoryScreen(navController, dataStoreManager) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    val isDarkTheme by ThemeManager.isDarkTheme
    FoodFinderTheme(darkTheme = isDarkTheme) {
        val navController = rememberNavController()
        InfoScreen(
            navController,
            product =
                Product.fromJson(
                    "{\"id\":1,\"image\":\"files/green_apple.jpg\",\"is_hot\":true," +
                        "\"name\":\"Яблоко зеленое\",\"quantity\":50,\"shop\":11}",
                ),
        )
    }
}
