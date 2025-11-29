package com.example.huertohogarapp.data.remote.dto.api

import com.example.huertohogarapp.data.models.Product
import com.example.huertohogarapp.R

fun ProductoDTO.toProductEntity(defaultImage: Int = R.drawable.imagen_default): Product {
    return Product(
        id = this.id?.toString() ?: "0",
        name = this.nombre ?: "Sin nombre",
        price = this.precio ?: 0.0,
        description = this.descripcion ?: "",
        category = this.categoria ?: "General",
        stock = this.stock ?: 0,
        imageUrl = "",
        imageRes = defaultImage
    )
}

