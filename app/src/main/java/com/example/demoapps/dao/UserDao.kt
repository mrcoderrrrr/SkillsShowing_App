package com.example.demoapps.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.demoapps.entity.UserEntity

@Dao
interface UserDao {
@Insert
fun userInsert(userEntity: UserEntity)

@Query("SELECT * FROM userinfo")
fun userViewData():List<UserEntity>

@Query("SELECT * FROM UserInfo WHERE id=:id")
fun userItemData(id:Int) : UserEntity
}