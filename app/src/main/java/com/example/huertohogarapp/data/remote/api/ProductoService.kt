package com.example.huertohogarapp.data.remote.dto.api

import retrofit2.http.GET
import retrofit2.http.Path

interface ProductoService {

    @GET("productos")
    suspend fun getProductos(): List<ProductoDTO>

    @GET("productos/{id}")
    suspend fun getProducto(@Path("id") id: Int): ProductoDTO

    @GET("productos/categorias")
    suspend fun getCategorias(): List<String>
}
