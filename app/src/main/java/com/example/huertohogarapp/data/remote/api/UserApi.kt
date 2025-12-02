package com.example.huertohogarapp.data.remote.api

import com.example.huertohogarapp.data.remote.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {

    @GET("api/usuarios")
    suspend fun getUsers(): List<UserDto>

    @GET("api/usuarios/{id}")
    suspend fun getUserById(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): UserDto

    @PUT("api/usuarios/{id}")
    suspend fun updateUser(
        @Path("id") id: Long,
        @Body user: UserDto
    ): UserDto

    @DELETE("api/usuarios/{id}")
    suspend fun deleteUser(
        @Path("id") id: Long,
        @Header("Authorization") token: String
    )



}
