package com.example.huertohogarapp.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.huertohogarapp.data.remote.dto.UserDto
import com.example.huertohogarapp.viewmodels.AdminUserViewModel
import com.example.huertohogarapp.viewmodels.AuthViewModel

@Composable
fun AdminUsersScreen(
    navController: NavController,
    viewModel: AdminUserViewModel,
    authViewModel: AuthViewModel
) {
    val users by viewModel.users.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    val token = authViewModel.token.collectAsState().value

    LaunchedEffect(token) {
        if (token != null) {
            viewModel.loadUsers(token)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4CAF50))
            .padding(16.dp)
    ) {

        Text(
            text = "Usuarios Registrados",
            color = Color.White,
            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = { navController.popBackStack() },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier.padding(bottom = 10.dp)
        ) {
            Text("Volver", color = Color(0xFF2E7D32))
        }

        when {
            loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.White)
                }
            }

            error != null -> {
                Text(
                    text = error ?: "",
                    color = Color.Red
                )
            }

            else -> {
                LazyColumn {
                    items(users) { user ->
                        UserCard(
                            user = user,
                            onClick = {
                                navController.navigate("adminUserDetail/${user.id}")
                            }
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun UserCard(
    user: UserDto,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("Nombre: ${user.nombre}", fontWeight = FontWeight.Bold)
            Text("Email: ${user.correo}")
            Text("Rol: ${user.rol}")
        }
    }
}

