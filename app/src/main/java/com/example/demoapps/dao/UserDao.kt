package com.example.demoapps.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.demoapps.entity.UserEntity

@Dao
interface UserDao {
@Insert
fun UserInsert(userEntity: UserEntity)
}