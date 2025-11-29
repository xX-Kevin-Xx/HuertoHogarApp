package com.example.huertohogarapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.huertohogarapp.data.models.CartItem

@Dao
interface CartDao {

    @Query("SELECT * FROM cart")
    suspend fun getCartItems(): List<CartItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartItem)

    @Query("DELETE FROM cart WHERE productId = :productId")
    suspend fun deleteByProductId(productId: String)

    @Query("DELETE FROM cart")
    suspend fun clearCart()

    @Update
    suspend fun updateItem(item: CartItem)

}
