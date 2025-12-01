package com.example.huertohogarapp.data.remote.api

import com.example.huertohogarapp.data.remote.dto.CreateProductRequest
import com.example.huertohogarapp.data.remote.dto.ProductDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductApi {

    @GET("api/productos")
    suspend fun getProducts(): List<ProductDto>

    @POST("api/productos")
    suspend fun createProduct(
        @Body product: ProductDto
    ): ProductDto

    @PUT("api/productos/{id}")
    suspend fun updateProduct(
        @Path("id") id: Long,
        @Body product: ProductDto
    ): ProductDto

    @DELETE("api/productos/{id}")
    suspend fun deleteProduct(@Path("id") id: Long)

}
