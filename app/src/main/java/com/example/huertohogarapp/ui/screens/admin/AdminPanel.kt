package com.example.huertohogarapp.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.huertohogarapp.ui.components.BackButton
import com.example.huertohogarapp.viewmodels.AuthViewModel

@Composable
fun AdminPanelScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F8E9))
            .padding(24.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(horizontal = 8.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = {
                    navController.navigate("welcome") {
                        popUpTo("welcome") { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C))
            ) {
                Text("Inicio", color = Color.White)
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    authViewModel.signOut()
                    navController.navigate("login") {
                        popUpTo("welcome") { inclusive = false }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
            ) {
                Text("Cerrar Sesión", color = Color.White)
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 80.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Panel de Administración",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32),
                modifier = Modifier.padding(bottom = 30.dp)
            )

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                Column(Modifier.padding(20.dp)) {
                    Text(
                        text = "Gestión de Productos",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E7D32)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text("Agrega, edita o elimina productos.", color = Color.Gray)
                    Spacer(Modifier.height(16.dp))

                    Button(
                        onClick = { navController.navigate("adminProducts") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Administrar Productos", color = Color.White)
                    }

                    Spacer(Modifier.height(10.dp))

                    Button(
                        onClick = { navController.navigate("adminAddProduct") },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                    ) {
                        Text("Agregar Producto", color = Color.White)
                    }

                }
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                Column(Modifier.padding(20.dp)) {
                    Text(
                        text = "Gestión de Usuarios",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E7D32)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text("Consulta los usuarios registrados.", color = Color.Gray)
                    Spacer(Modifier.height(16.dp))

                    Button(
                        onClick = { navController.navigate("adminUsers") },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Ver Usuarios", color = Color.White)
                    }
                }
            }
        }
    }
}
