package com.example.huertohogarapp.ui.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.huertohogarapp.data.remote.api.ProductApi
import com.example.huertohogarapp.data.remote.dto.ProductDto
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductScreen(
    navController: NavController,
    productApi: ProductApi,
    productId: Long
) {
    val scope = rememberCoroutineScope()

    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    var product by remember { mutableStateOf<ProductDto?>(null) }

    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var categoria by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var imagenUrl by remember { mutableStateOf("") }

    LaunchedEffect(productId) {
        try {
            val p = productApi.getProducts().find { it.id == productId }
            if (p == null) {
                error = "Producto no encontrado."
            } else {
                product = p
                nombre = p.nombre
                descripcion = p.descripcion
                precio = p.precio.toString()
                categoria = p.categoria
                stock = p.stock.toString()
                imagenUrl = p.imagenUrl ?: ""
            }
        } catch (e: Exception) {
            error = "Error al cargar producto: ${e.message}"
        } finally {
            loading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Producto") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
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

        if (loading) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color(0xFF4CAF50))
            }
            return@Scaffold
        }

        error?.let {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(it, color = Color.Red)
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (imagenUrl.isNotBlank()) {
                AsyncImage(
                    model = imagenUrl,
                    contentDescription = nombre,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                )
                Spacer(Modifier.height(10.dp))
            }

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = descripcion,
                onValueChange = { descripcion = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = categoria,
                onValueChange = { categoria = it },
                label = { Text("Categoría") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = stock,
                onValueChange = { stock = it },
                label = { Text("Stock") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = imagenUrl,
                onValueChange = { imagenUrl = it },
                label = { Text("URL de Imagen") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    scope.launch {
                        try {
                            loading = true

                            val updated = ProductDto(
                                id = productId,
                                nombre = nombre,
                                descripcion = descripcion,
                                categoria = categoria,
                                precio = precio.toDoubleOrNull() ?: 0.0,
                                stock = stock.toIntOrNull() ?: 0,
                                imagenUrl = imagenUrl
                            )

                            productApi.updateProduct(productId, updated)

                            navController.navigate("adminProducts") {
                                popUpTo("adminProducts") { inclusive = true }
                            }

                        } catch (e: Exception) {
                            error = "Error al actualizar: ${e.message}"
                        } finally {
                            loading = false
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text("Guardar Cambios", color = Color.White)
            }
        }
    }
}
