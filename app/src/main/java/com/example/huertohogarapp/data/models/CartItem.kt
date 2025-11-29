package com.example.huertohogarapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartItem(
    @PrimaryKey val productId: String,
    val productName: String,
    val price: Double,
    val quantity: Int,
    val imageRes: Int? = null
){
    fun subtotal(): Double = price * quantity

    fun formatSubtotal(): String = "$${String.format("%.0f", subtotal())} CLP"
}
