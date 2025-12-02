package com.example.huertohogarapp.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.huertohogarapp.viewmodel.UserViewModel
import com.example.huertohogarapp.viewmodels.AuthViewModel
import com.example.huertohogarapp.R
import com.example.huertohogarapp.ui.components.BackButton

@Composable
fun LoginScreen(
    navController: NavController,
    userViewModel: UserViewModel,
    authViewModel: AuthViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val token by authViewModel.token.collectAsState()
    val loginError by authViewModel.error.collectAsState()

    val role by authViewModel.role.collectAsState()

    LaunchedEffect(token, role) {
        if (token != null && role != null) {
            if (role == "ADMIN") {
                navController.navigate("adminPanel") {
                    popUpTo("login") { inclusive = true }
                }
            } else {
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
    }

    val isUserNotRegistered = loginError == "NO_AUTH"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4CAF50))
    ) {

        BackButton(
            navController = navController,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        )

        if (isUserNotRegistered) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    "Usuario no registrado o contraseña incorrecta",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 30.dp)
                )

                Button(
                    onClick = {
                        authViewModel.clearError()
                        navController.navigate("register")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrarse ahora", color = Color(0xFF2E7D32))
                }

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {
                        authViewModel.clearError()
                        navController.navigate("welcome")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Volver al inicio", color = Color.White)
                }
            }
            return
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Iniciar Sesión",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                singleLine = true,
                visualTransformation = if (passwordVisible)
                    VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(
                                id = if (passwordVisible) R.drawable.ojo_cerrado else R.drawable.ojo_abierto
                            ),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    cursorColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) return@Button
                    authViewModel.login(email, password)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "Ingresar",
                    color = Color(0xFF2E7D32),
                    fontWeight = FontWeight.Bold
                )
            }

            loginError?.let { msg ->
                Text(
                    text = if (msg != "NO_AUTH") msg else "",
                    color = Color.White,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }
        }
    }
}
