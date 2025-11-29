package com.example.huertohogarapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.huertohogarapp.data.models.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM productos")
    suspend fun getAllProducts(): List<Product>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<Product>)

    @Query("DELETE FROM productos")
    suspend fun clearAll()

}
