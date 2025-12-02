package com.example.huertohogarapp.data.remote.dto

data class RegisterRequest(
    val nombre: String,
    val correo: String,
    val password: String,
    val telefono: String,
    val direccion: String
)
