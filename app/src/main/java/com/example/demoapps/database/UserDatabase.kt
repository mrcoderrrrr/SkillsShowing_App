package com.example.demoapps.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.demoapps.dao.UserDao
import com.example.demoapps.entity.UserEntity
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [UserEntity::class], version = 1)
abstract class UserDatabase:RoomDatabase() {
    abstract fun userDao(): UserDao

}