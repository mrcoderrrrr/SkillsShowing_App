package com.example.demoapps.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserInfo")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? =0,
    val fname:String,
    val lname:String,
    val gender:String,
    val dob: String,
    val imagepath: String
    )