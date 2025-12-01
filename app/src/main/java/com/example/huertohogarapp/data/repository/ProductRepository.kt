package com.example.huertohogarapp.data.repository

import com.example.huertohogarapp.data.database.ProductDao
import com.example.huertohogarapp.data.remote.RetrofitClient
import com.example.huertohogarapp.data.remote.dto.toProductEntity

class ProductRepository(private val dao: ProductDao) {

    suspend fun syncProducts(defaultImageRes: Int) {
        val remoteProducts = RetrofitClient.productApi.getProducts()

        val entities = remoteProducts.map { dto ->
            dto.toProductEntity()
        }

        dao.clearAll()

        dao.insertAll(entities)
    }

    suspend fun getLocalProducts() = dao.getAllProducts()
}
