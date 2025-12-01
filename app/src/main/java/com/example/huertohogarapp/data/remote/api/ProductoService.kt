package com.example.huertohogarapp.data.remote.dto.api

import com.example.huertohogarapp.data.remote.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductoService {

    @GET("productos")
    suspend fun getProductos(): List<ProductDto>

    @GET("productos/{id}")
    suspend fun getProducto(@Path("id") id: Int): ProductDto

    @GET("productos/categorias")
    suspend fun getCategorias(): List<String>
}
