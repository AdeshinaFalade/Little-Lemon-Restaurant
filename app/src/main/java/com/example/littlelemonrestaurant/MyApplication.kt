package com.example.littlelemonrestaurant

import android.app.Application
import androidx.room.Room.databaseBuilder


class MyApplication : Application() {

    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        database = databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app_database"
        ).build()
    }

}