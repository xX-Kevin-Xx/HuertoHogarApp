package com.example.huertohogarapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.huertohogarapp.data.remote.RetrofitClient
import com.example.huertohogarapp.data.remote.dto.ProductDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminProductViewModel : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private val _success = MutableStateFlow<Boolean>(false)
    val success = _success.asStateFlow()

    fun addProduct(
        nombre: String,
        descripcion: String,
        precio: Double,
        categoria: String,
        stock: Int,
        imagenUrl: String
    ) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null
                _success.value = false

                val dto = ProductDto(
                    id = null,
                    nombre = nombre,
                    descripcion = descripcion,
                    precio = precio,
                    categoria = categoria,
                    stock = stock,
                    imagenUrl = imagenUrl.ifBlank { null }
                )

                RetrofitClient.productApi.createProduct(dto)
                _success.value = true

            } catch (e: Exception) {
                _error.value = "Error al guardar producto: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun clearStatus() {
        _success.value = false
        _error.value = null
    }
}
