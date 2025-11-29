package com.example.huertohogarapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class User(
    @PrimaryKey val id: String,
    val email: String,
    val name: String,
    val address: String = "",
    val phone: String = "",
    val password: String = "",
    val pais: String = ""
)
