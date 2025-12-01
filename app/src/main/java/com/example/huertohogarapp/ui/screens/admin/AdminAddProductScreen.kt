package com.example.huertohogarapp.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.huertohogarapp.viewmodels.AdminProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminAddProductScreen(
    navController: NavController,
    viewModel: AdminProductViewModel
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var stock by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Agregar Producto",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4CAF50)
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .background(Color(0xFFF1F8E9))
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Nuevo Producto",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF2E7D32)
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (imageUrl.isNotBlank()) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = "Vista previa",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            AddProductTextField("Nombre", name) { name = it }
            AddProductTextField("Descripción", description) { description = it }
            AddProductTextField("Precio", price) { price = it }
            AddProductTextField("Categoría", category) { category = it }
            AddProductTextField("Stock", stock) { stock = it }
            AddProductTextField("URL de la imagen", imageUrl) { imageUrl = it }

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    viewModel.addProduct(
                        name, description, price.toDoubleOrNull() ?: 0.0,
                        category, stock.toIntOrNull() ?: 0, imageUrl
                    )
                    navController.popBackStack()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar Producto", color = Color.White, fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun AddProductTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = Color(0xFF2E7D32)) },
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF4CAF50),
            unfocusedBorderColor = Color(0xFF81C784),
            cursorColor = Color(0xFF2E7D32),
            focusedLabelColor = Color(0xFF2E7D32)
        )
    )
    Spacer(modifier = Modifier.height(10.dp))
}
