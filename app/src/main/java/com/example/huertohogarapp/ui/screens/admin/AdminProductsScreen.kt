package com.example.huertohogarapp.ui.screens.admin

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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.huertohogarapp.data.remote.api.ProductApi
import com.example.huertohogarapp.data.remote.dto.ProductDto
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

        when {
            loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color(0xFF2E7D32))
                }
            }

            error != null -> {
                Text(text = error!!, color = Color.Red)
            }

            else -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(products) { product ->
                        ProductAdminItem(
                            product = product,
                            onDelete = { productId ->
                                scope.launch {
                                    try {
                                        productApi.deleteProduct(productId)
                                        products = products.filterNot { it.id == productId }
                                    } catch (e: Exception) {
                                        error = "Error al eliminar producto."
                                    }
                                }
                            },
                            onEdit = { productId ->
                                navController.navigate("editProduct/$productId")
                            }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
    }
}


@Composable
fun ProductAdminItem(
    product: ProductDto,
    onDelete: (Long) -> Unit,
    onEdit: (Long) -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = product.nombre,
                fontSize = 20.sp,
                color = Color(0xFF2E7D32),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text("Descripción: ${product.descripcion}")
            Text("Precio: $${product.precio}")
            Text("Stock: ${product.stock}")

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                Button(
                    onClick = { onEdit(product.id ?: 0L) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Editar", color = Color.White)
                }

                Spacer(modifier = Modifier.width(10.dp))

                Button(
                    onClick = { onDelete(product.id ?: 0L) },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Eliminar", color = Color.White)
                }
            }
        }
    }
}
