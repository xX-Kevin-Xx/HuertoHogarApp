package com.example.huertohogarapp.data.database

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "huertohogar_db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }
        return INSTANCE!!
    }
}
