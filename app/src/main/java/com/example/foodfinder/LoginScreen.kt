package com.example.foodfinder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        BasicTextField(
            value = username,
            onValueChange = { username = it },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            singleLine = true,
            decorationBox = { innerTextField ->
                Box(
                    modifier =
                        Modifier
                            .background(Color.Gray)
                            .padding(8.dp),
                ) {
                    if (username.isEmpty()) {
                        Text("Username", color = Color.LightGray)
                    }
                    innerTextField()
                }
            },
        )
        BasicTextField(
            value = password,
            onValueChange = { password = it },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            decorationBox = { innerTextField ->
                Box(
                    modifier =
                        Modifier
                            .background(Color.Gray)
                            .padding(8.dp),
                ) {
                    if (password.isEmpty()) {
                        Text("Password", color = Color.LightGray)
                    }
                    innerTextField()
                }
            },
        )
        Button(
            onClick = { navController.navigate("scanner") },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Login")
        }
    }
}
