package com.example.demoapps.model

import androidx.room.PrimaryKey

data class SignUpModel(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var name:String,
    var email:String,
    var gender:String,
    var dob:String,
)
