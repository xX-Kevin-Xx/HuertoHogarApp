package com.example.huertohogarapp.data.remote.dto

data class ProductDto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val categoria: String,
    val stock: Int
)
