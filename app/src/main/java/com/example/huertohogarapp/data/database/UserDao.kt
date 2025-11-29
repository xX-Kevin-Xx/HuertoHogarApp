package com.example.huertohogarapp.data.database

import androidx.room.*
import com.example.huertohogarapp.data.models.User

@Dao
interface UserDao {
    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)
}
