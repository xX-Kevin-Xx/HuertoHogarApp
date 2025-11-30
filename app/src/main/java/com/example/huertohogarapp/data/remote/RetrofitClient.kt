package com.example.huertohogarapp.data.remote

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.huertohogarapp.data.remote.api.ProductApi
import com.example.huertohogarapp.data.remote.api.UserApi
import com.example.huertohogarapp.data.remote.api.AuthApi

object RetrofitClient {

    private const val BASE_URL = "http://192.168.0.199:8080/"

    private var token: String? = null

    fun setToken(value: String) {
        token = value
    }

    private val authInterceptor = Interceptor { chain ->
        val original = chain.request()
        val requestBuilder = original.newBuilder()

        token?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        chain.proceed(requestBuilder.build())
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val productApi: ProductApi by lazy {
        retrofit.create(ProductApi::class.java)
    }

    val userApi: UserApi by lazy {
        retrofit.create(UserApi::class.java)
    }

    val authApi: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }


}
