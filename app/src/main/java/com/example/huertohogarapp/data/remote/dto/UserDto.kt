package com.example.huertohogarapp.data.remote.dto

data class UserDto(
    val id: Long,
    val nombre: String,
    val correo: String,
    val rol: String,
    val telefono: String?,
    val direccion: String?
)

