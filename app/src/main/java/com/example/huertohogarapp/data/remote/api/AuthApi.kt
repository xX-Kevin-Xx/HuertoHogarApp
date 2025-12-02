package com.example.huertohogarapp.data.remote.api

import com.example.huertohogarapp.data.remote.dto.LoginRequest
import com.example.huertohogarapp.data.remote.dto.LoginResponse
import com.example.huertohogarapp.data.remote.dto.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest)
}
