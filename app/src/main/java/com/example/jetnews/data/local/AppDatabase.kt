package com.example.jetnews.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetnews.data.model.UserDao
import com.example.jetnews.data.model.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}