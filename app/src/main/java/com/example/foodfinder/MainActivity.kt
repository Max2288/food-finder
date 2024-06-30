package com.example.foodfinder

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.foodfinder.model.InfoParamType
import com.example.foodfinder.model.Product
import com.example.foodfinder.network.RetrofitInstance
import com.example.foodfinder.ui.theme.FoodFinderTheme
import kotlinx.coroutines.launch

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
            composable("scanner") { BarcodeScannerScreen(navController) }
            composable(
                "info/{product}",
                arguments =
                    listOf(
                        navArgument("product") { type = InfoParamType() },
                    ),
            ) {
                val product = it.arguments?.getParcelable<Product>("product")
                InfoScreen(navController, product)
            }
            composable("history") { HistoryScreen(navController) }
        }
    }
}

@Composable
fun BarcodeScannerScreen(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Здесь будет сканер штрихкодов")
            Button(
                onClick = {
                    coroutineScope.launch {
                        val response = RetrofitInstance.api.getProduct(3)
                        if (response.isSuccessful) {
                            val product = response.body()
                            val productJson = Uri.encode(Product.toJson(product))
                            navController.navigate("info/$productJson")
                        }
                    }
                },
                modifier = Modifier.padding(16.dp),
            ) {
                Text("Перейти далее")
            }
        }
        IconButton(
            onClick = { navController.navigate("history") },
            modifier = Modifier.align(Alignment.TopEnd),
        ) {
            Icon(Icons.Default.Menu, contentDescription = "History")
        }
    }
}

// "info/product_id"

@Composable
fun InfoScreen(
    navController: NavController,
    product: Product?,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
        }
        Text(text = "Информация о продукте")
        product?.let {
            Text(text = "ID: ${it.id}")
            Text(text = "Name: ${it.name}")
            Text(text = "Quantity: ${it.quantity}")
            Text(text = "Is Hot: ${it.is_hot}")
            Text(text = "Shop: ${it.shop}")
        }
    }
}

@Composable
fun HistoryScreen(navController: NavController) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
        }
        Text(text = "История просмотров")
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    FoodFinderTheme {
        val navController = rememberNavController()
        LoginScreen(navController)
    }
}
