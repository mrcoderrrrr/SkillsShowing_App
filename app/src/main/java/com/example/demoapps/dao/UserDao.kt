package com.example.demoapps.dao

import androidx.room.*
import com.example.demoapps.entity.UserEntity

@Dao
interface UserDao {
@Insert(onConflict = OnConflictStrategy.REPLACE)
fun userInsert(userEntity: UserEntity)

@Query("SELECT * FROM userinfo")
fun userViewData():List<UserEntity>

@Query("SELECT * FROM UserInfo WHERE id=:id")
fun userItemData(id: Int) : UserEntity

@Update
fun userUpdate(userEntity: UserEntity)
}