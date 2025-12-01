package com.example.huertohogarapp.data.remote.dto

import com.example.huertohogarapp.data.models.Product
import com.example.huertohogarapp.R

fun ProductDto.toProductEntity(defaultImage: Int = R.drawable.imagen_default): Product {
    return Product(
        id = this.id.toString(),
        name = this.nombre,
        price = this.precio,
        description = this.descripcion,
        category = this.categoria,
        stock = this.stock,
        imageUrl = this.imagenUrl ?: ""
    )
}
