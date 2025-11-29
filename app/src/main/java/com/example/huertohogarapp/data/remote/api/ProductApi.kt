package com.example.huertohogarapp.data.remote.api

import com.example.huertohogarapp.data.remote.dto.ProductDto
import retrofit2.http.GET

interface ProductApi {

    @GET("api/productos")
    suspend fun getProducts(): List<ProductDto>
}
