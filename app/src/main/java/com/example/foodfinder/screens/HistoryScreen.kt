package com.example.foodfinder.screens

import DataStoreManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.foodfinder.R
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun HistoryScreen(
    navController: NavController,
    dataStoreManager: DataStoreManager,
) {
    val productHistory by dataStoreManager.productHistory.collectAsState(initial = emptyList())

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

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.historyView),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (productHistory.isEmpty()) {
            Text(
                text = stringResource(R.string.historyEmpty),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
        } else {
            productHistory.forEach { scannedProduct ->
                Column(
                    modifier =
                        Modifier
                            .padding(8.dp)
                            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small)
                            .padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = "Название: ${scannedProduct.product.name}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text = "Количество: ${scannedProduct.product.quantity}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                    Text(
                        text =
                            Instant.ofEpochMilli(scannedProduct.time).atZone(ZoneId.systemDefault())
                                .format(DateTimeFormatter.ofPattern("HH:mm")),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }
    }
}
