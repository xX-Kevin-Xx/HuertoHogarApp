package com.example.huertohogarapp.data.remote.dto

data class CreateProductRequest(
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val categoria: String,
    val stock: Int,
    val imagenUrl: String
)
