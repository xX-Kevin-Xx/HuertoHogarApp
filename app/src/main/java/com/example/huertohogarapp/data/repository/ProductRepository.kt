package com.example.huertohogarapp.data.repository

import com.example.huertohogarapp.data.database.ProductDao
import com.example.huertohogarapp.data.remote.RetrofitClient
import com.example.huertohogarapp.data.remote.dto.toProductEntity

class ProductRepository(private val dao: ProductDao) {

    suspend fun syncProducts(defaultImageRes: Int) {
        // 1. Obtener los productos desde el backend
        val remoteProducts = RetrofitClient.productApi.getProducts()

        // 2. Convertir DTO → Entity
        val entities = remoteProducts.map { dto ->
            dto.toProductEntity(defaultImageRes)
        }

        // 3. Limpiar la base de datos antes de insertar los productos nuevos
        dao.clearAll()

        // 4. Guardar en Room
        dao.insertAll(entities)
    }

    suspend fun getLocalProducts() = dao.getAllProducts()
}
