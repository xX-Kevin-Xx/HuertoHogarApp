package com.example.huertohogarapp.data.repository

import android.content.Context
import com.example.huertohogarapp.data.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val context: Context) {

    private val users = mutableListOf(
        User(
            id = "U001",
            email = "admin@huertohogar.cl",
            name = "Administrador",
            address = "Santiago, Chile",
            phone = "+56 9 1234 5678",
            pais = "Chile"
        )
    )

    suspend fun getUserByEmail(email: String): User? = withContext(Dispatchers.IO) {
        users.find { it.email.equals(email, ignoreCase = true) }
    }

    suspend fun insertUser(user: User) = withContext(Dispatchers.IO) {
        users.add(user)
    }

    suspend fun getAllUsers(): List<User> = withContext(Dispatchers.IO) {
        users.toList()
    }
}
