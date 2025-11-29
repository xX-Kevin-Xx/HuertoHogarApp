package com.example.huertohogarapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.huertohogarapp.data.database.AppDatabase
import com.example.huertohogarapp.data.repository.ProductRepository
import com.example.huertohogarapp.ui.screens.auth.theme.HuertoHogarAppTheme
import com.example.huertohogarapp.viewmodel.ProductViewModel
import com.example.huertohogarapp.viewmodels.ProductViewModelFactory
import com.example.huertohogarapp.navigation.AppNavigation

class MainActivity : ComponentActivity() {

    // --- Inicializamos el ViewModel aquí ---
    private val productViewModel: ProductViewModel by viewModels {
        val dao = AppDatabase.getDatabase(this).productDao()
        val repo = ProductRepository(dao)
        ProductViewModelFactory(repo)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HuertoHogarAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 👉 Pasamos el ViewModel a la navegación
                    AppNavigation(productViewModel)
                }
            }
        }
    }
}
