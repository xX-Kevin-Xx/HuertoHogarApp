package com.example.huertohogarapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class Product(
    @PrimaryKey val id: String,
    val name: String,
    val price: Double,
    val description: String,
    val category: String,
    val stock: Int,
    val imageUrl: String = "",
    val imageRes: Int

) {
    fun formatPrice(): String = "$${String.format("%.0f", price)} CLP"
}
