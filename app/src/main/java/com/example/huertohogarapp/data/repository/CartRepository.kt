package com.example.huertohogarapp.data.repository

import android.content.Context
import com.example.huertohogarapp.data.database.AppDatabase
import com.example.huertohogarapp.data.models.CartItem

class CartRepository(context: Context) {

    private val cartDao = AppDatabase.getDatabase(context).cartDao()

    suspend fun getCartItems(): List<CartItem> {
        return cartDao.getCartItems()
    }

    suspend fun addToCart(item: CartItem) {
        cartDao.insert(item)
    }

    suspend fun deleteByProductId(productId: String) {
        cartDao.deleteByProductId(productId)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }
}
