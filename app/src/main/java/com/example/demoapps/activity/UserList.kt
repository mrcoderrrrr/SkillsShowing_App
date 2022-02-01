package com.example.demoapps.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.demoapps.R
import com.example.demoapps.database.UserDatabase
import com.example.demoapps.databinding.ActivityUserListBinding
import com.example.demoapps.entity.UserEntity
import com.squareup.picasso.Picasso

class UserList : AppCompatActivity() {
    private lateinit var dataBinding:ActivityUserListBinding
    var userId: Int? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       dataBinding=DataBindingUtil.setContentView(this,R.layout.activity_user_list)
        userId= intent.getIntExtra("UserId",0).toInt()
        Log.d("userValue", userId!!.toInt().toString())
        viewData()
    }

    private fun viewData() {
        var user=UserDatabase.getInstance(this)?.userDao()!!.userItemData(userId!!.toInt())
       Picasso.get()
           .load(user.imagepath)
           .resize(200,200)
           .centerCrop()
           .into(dataBinding.cvProfile)
        dataBinding.tvName.setText(user.fname+" "+user.lname)
        dataBinding.tvGender.setText(user.gender)
        dataBinding.tvDob.setText(user.dob)
    }
}