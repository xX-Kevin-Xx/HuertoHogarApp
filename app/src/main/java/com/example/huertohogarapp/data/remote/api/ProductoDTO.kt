package com.example.huertohogarapp.data.remote.dto.api

data class ProductoDTO(
    val id: Int?,           // Nullable
    val nombre: String?,    // Nullable
    val precio: Double?,    // Nullable
    val descripcion: String?, // Nullable
    val categoria: String?,  // Nullable
    val stock: Int?         // Nullable
)

