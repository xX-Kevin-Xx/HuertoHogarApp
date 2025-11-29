package com.example.huertohogarapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.huertohogarapp.data.models.Product
import com.example.huertohogarapp.ui.components.ProductCard
import com.example.huertohogarapp.viewmodel.ProductViewModel
import com.example.huertohogarapp.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: ProductViewModel,
    cartViewModel: CartViewModel,
    onProductClick: (Product) -> Unit,
    onNavigateToCart: () -> Unit
) {
    // CORREGIDO: usar products en lugar de productList
    val productList by viewModel.products.collectAsState()
    val itemCount by cartViewModel.cartItemCount.collectAsState(initial = 0)
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var selectedCategory by remember { mutableStateOf("Todas") }

    // Cargar productos al entrar
    LaunchedEffect(Unit) {
        viewModel.loadProducts()
    }

    val categories = listOf(
        "Todas",
        "Frutas Frescas",
        "Verduras Orgánicas",
        "Productos Orgánicos",
        "Productos Lácteos"
    )

    val filteredProducts = remember(searchQuery.text, selectedCategory, productList) {
        productList.filter { product ->
            val matchesCategory = selectedCategory == "Todas" || product.category == selectedCategory
            val matchesSearch = product.name.contains(searchQuery.text, ignoreCase = true) ||
                    product.category.contains(searchQuery.text, ignoreCase = true)
            matchesCategory && matchesSearch
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Huerto Hogar") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color(0xFF4CAF50)
                        )
                    }
                },
                actions = {
                    Box {
                        IconButton(onClick = onNavigateToCart) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Ver carrito",
                                tint = Color(0xFF4CAF50)
                            )
                        }
                        if (itemCount > 0) {
                            Text(
                                text = itemCount.toString(),
                                color = Color.White,
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .background(Color(0xFF4CAF50), shape = MaterialTheme.shapes.small)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE8F5E9))
                .padding(padding)
        ) {
            // Buscador
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Buscar productos...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFF4CAF50),
                    cursorColor = Color(0xFF4CAF50)
                )
            )

            val scrollState = rememberScrollState()

            // Categorías
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState)
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        label = { Text(category) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF4CAF50),
                            selectedLabelColor = Color.White,
                            containerColor = Color.White,
                            labelColor = Color(0xFF4CAF50)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            if (filteredProducts.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No se encontraron productos 😢")
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize()
                ) {
                    items(filteredProducts) { product ->
                        ProductCard(
                            product = product,
                            onClick = { onProductClick(product) }
                        )
                    }
                }
            }
        }
    }
}
