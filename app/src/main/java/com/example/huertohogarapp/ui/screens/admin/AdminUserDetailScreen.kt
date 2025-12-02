package com.example.huertohogarapp.ui.screens.admin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huertohogarapp.viewmodels.AdminUserViewModel
import com.example.huertohogarapp.viewmodels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminUserDetailScreen(
    navController: NavController,
    viewModel: AdminUserViewModel,
    authViewModel: AuthViewModel,
    userId: Long
) {
    val user by viewModel.selectedUser.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    var showDeleteDialog by remember { mutableStateOf(false) }

    LaunchedEffect(userId) {
        val token = authViewModel.token.value ?: ""
        viewModel.loadUserById(userId, token)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Usuario") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4CAF50),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxWidth()
        ) {

            if (loading) {
                CircularProgressIndicator(color = Color(0xFF4CAF50))
                return@Column
            }

            error?.let {
                Text(it, color = Color.Red)
            }

            user?.let { u ->

                Text("Nombre: ${u.nombre}", style = MaterialTheme.typography.titleMedium)
                Text("Correo: ${u.correo}")
                Text("Teléfono: ${u.telefono ?: "-"}")
                Text("Dirección: ${u.direccion ?: "-"}")

                Spacer(Modifier.height(16.dp))

                var rol by remember { mutableStateOf(u.rol) }
                var expanded by remember { mutableStateOf(false) }

                Box(modifier = Modifier.fillMaxWidth()) {

                    OutlinedTextField(
                        value = rol,
                        onValueChange = {},
                        label = { Text("Rol del Usuario") },
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = true },
                        trailingIcon = {
                            IconButton(onClick = { expanded = true }) {
                                Icon(
                                    Icons.Default.ArrowDropDown,
                                    contentDescription = "Seleccionar rol"
                                )
                            }
                        }
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("USER") },
                            onClick = {
                                rol = "USER"
                                expanded = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("ADMIN") },
                            onClick = {
                                rol = "ADMIN"
                                expanded = false
                            }
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {
                        viewModel.updateUserRole(u.id, rol)
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                    enabled = u.rol != "ADMIN"
                ) {
                    Text("Actualizar Rol", color = Color.White)
                }

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = { showDeleteDialog = true },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                    enabled = u.rol != "ADMIN"
                ) {
                    Text("Eliminar Usuario", color = Color.White)
                }

                if (showDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = { showDeleteDialog = false },
                        title = { Text("Confirmar eliminación") },
                        text = { Text("¿Estás seguro de eliminar este usuario?") },
                        confirmButton = {
                            TextButton(onClick = {
                                viewModel.deleteUser(
                                    u.id,
                                    authViewModel.token.value ?: ""
                                )
                                showDeleteDialog = false

                                navController.navigate("adminUsers") {
                                    popUpTo("adminUsers") { inclusive = true }
                                }
                            }) {
                                Text("Eliminar", color = Color.Red)
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDeleteDialog = false }) {
                                Text("Cancelar")
                            }
                        }
                    )
                }

                if (showDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = { showDeleteDialog = false },
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    showDeleteDialog = false
                                    viewModel.deleteUser(u.id, authViewModel.token.value ?: "")
                                    navController.navigate("adminUsers") {
                                        popUpTo("adminUsers") { inclusive = true }
                                    }
                                }
                            ) {
                                Text("Eliminar", color = Color.Red)
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDeleteDialog = false }) {
                                Text("Cancelar")
                            }
                        },
                        title = { Text("Confirmar eliminación") },
                        text = { Text("¿Seguro que deseas eliminar este usuario?") }
                    )
                }
            }
        }
    }
}
