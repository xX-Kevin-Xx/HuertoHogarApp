package com.example.huertohogarapp.ui.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.huertohogarapp.data.remote.dto.ProductDto
import com.example.huertohogarapp.data.remote.api.ProductApi
import kotlinx.coroutines.launch

@Composable
fun AdminProductsScreen(
    navController: NavController,
    productApi: ProductApi
) {

    var products by remember { mutableStateOf<List<ProductDto>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                products = productApi.getProducts()
                loading = false
            } catch (e: Exception) {
                error = "Error al cargar productos."
                loading = false
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                text = "Productos Disponibles",
                color = Color(0xFF2E7D32),
                fontSize = 26.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { navController.navigate("adminPanel") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White
                )
            ) {
                Text("Volver al Panel")
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (loading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF2E7D32))
                }
                return@Column
            }

            error?.let {
                Text(text = it, color = Color.Red)
                return@Column
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(products) { product ->
                    ProductAdminItem(product)
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun ProductAdminItem(product: ProductDto) {

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFE8F5E9)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = product.nombre,
                fontSize = 20.sp,
                color = Color(0xFF2E7D32),
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(text = "Descripción: ${product.descripcion}")
            Text(text = "Precio: $${product.precio}")
            Text(text = "Stock: ${product.stock}")
        }
    }
}

