package com.example.huertohogarapp.data.remote.dto

import com.example.huertohogarapp.data.models.Product

fun ProductDto.toProductEntity(defaultImage: Int): Product {
    return Product(
        id = this.id.toString(),
        name = this.nombre,
        price = this.precio,
        description = this.descripcion,
        category = this.categoria,
        stock = this.stock,
        imageUrl = "",
        imageRes = defaultImage
    )
}
