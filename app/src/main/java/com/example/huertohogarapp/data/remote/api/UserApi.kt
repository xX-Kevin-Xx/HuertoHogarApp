package com.example.huertohogarapp.data.remote.api

import com.example.huertohogarapp.data.remote.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Header

interface UserApi {

    @GET("api/usuarios")
    suspend fun getUsers(
        @Header("Authorization") token: String
    ): List<UserDto>
}
