package com.example.foodfinder.screens

import DataStoreManager
import android.app.AlertDialog
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.foodfinder.model.Product
import com.example.foodfinder.network.RetrofitInstance
import com.google.android.gms.vision.barcode.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.launch

@Composable
fun BarcodeScannerScreen(
    navController: NavController,
    dataStoreManager: DataStoreManager,
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    var scanError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        val options =
            GmsBarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                    Barcode.QR_CODE,
                    Barcode.AZTEC,
                )
                .build()
        val scanner = GmsBarcodeScanning.getClient(context, options)
        scanner.startScan()
            .addOnSuccessListener { barcode ->
                val rawValue: String? = barcode.rawValue
                if (rawValue != null) {
                    coroutineScope.launch {
                        try {
                            val response = RetrofitInstance.api.getProduct(rawValue.toInt())
                            if (response.isSuccessful) {
                                val product = response.body()
                                val productJson = Uri.encode(Product.toJson(product))
                                navController.navigate("info/$productJson")
                                product?.let { product ->
                                    dataStoreManager.saveProductToHistory(product)
                                }
                            } else {
                                scanError = "Failed to fetch product information."
                            }
                        } catch (e: Exception) {
                            scanError = "Error fetching product: ${e.message}"
                        }
                    }
                } else {
                    scanError = "Scanned value is empty."
                }
            }
            .addOnCanceledListener {
                navController.navigate("mainMenu")
            }
            .addOnFailureListener { e ->
                scanError = "Error occurred while scanning: ${e.message}"
            }
    }
    scanError?.let { error ->
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder
            .setMessage(error)
            .setTitle("Произошла ошибка")
            .setNegativeButton("Вернутся") { dialog, which ->
                navController.navigate("mainMenu")
            }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}
