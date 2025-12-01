package com.example.huertohogarapp.ui.screens.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.huertohogarapp.R
import com.example.huertohogarapp.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController, cartViewModel: CartViewModel) {
    val cartItems by cartViewModel.cartItems.collectAsState()
    val total by remember(cartItems) { mutableStateOf(cartViewModel.getTotal()) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrito de compras") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (cartItems.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Tu carrito está vacío 🛒")
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(cartItems) { item ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFDFFFE0))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (item.imageUrl.isNotBlank()) {
                                    AsyncImage(
                                        model = item.imageUrl,
                                        contentDescription = item.productName,
                                        modifier = Modifier
                                            .size(64.dp)
                                            .padding(end = 8.dp)
                                    )
                                } else {
                                    Image(
                                        painter = painterResource(id = R.drawable.placeholder),
                                        contentDescription = item.productName,
                                        modifier = Modifier
                                            .size(64.dp)
                                            .padding(end = 8.dp)
                                    )
                                }

                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 4.dp)
                                ) {
                                    Text(
                                        text = item.productName,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF1B5E20),
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Text(
                                        text = "Cantidad: ${item.quantity}",
                                        color = Color(0xFF2E7D32),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = "Precio: $${item.price * item.quantity}",
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF388E3C),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }

                                IconButton(onClick = { cartViewModel.removeItem(item.productId) }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Eliminar",
                                        tint = Color(0xFFB71C1C)
                                    )
                                }
                            }

                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Total: $${"%.0f".format(total)}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20)
                )

                Button(
                    onClick = {
                        cartViewModel.clearCart()
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                ) {
                    Text("Finalizar compra ✅")
                }
            }
        }
    }
}
