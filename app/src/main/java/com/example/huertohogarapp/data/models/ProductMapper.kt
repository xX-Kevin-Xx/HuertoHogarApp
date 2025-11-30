package com.example.huertohogarapp.data.models

import com.example.huertohogarapp.data.remote.dto.ProductDto

fun ProductDto.toEntity(): Product {
    return Product(
        id = this.id.toString(),
        name = this.nombre,
        price = this.precio,
        description = this.descripcion,
        category = this.categoria,
        stock = this.stock,
        imageRes = 0
    )
}
