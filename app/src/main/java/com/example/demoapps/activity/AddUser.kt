package com.example.demoapps.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import com.example.demoapps.R
import com.example.demoapps.database.UserDatabase
import com.example.demoapps.databinding.ActivityAddUserBinding
import com.example.demoapps.entity.UserEntity
import java.text.SimpleDateFormat
import java.util.*

class AddUser : AppCompatActivity() {
    private lateinit var dataBinding: ActivityAddUserBinding
    private lateinit var userDatabase: UserDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_user)


        setClick()
    }

    private fun setClick() {
        dateOfBirth()
        profileImage()
        dataBinding.btnSubmit.setOnClickListener{
            insertData()
        }
    }

    private fun profileImage() {
      dataBinding.ciProfile.setOnClickListener{
          val intent=Intent(Intent.ACTION_PICK)
          intent.type="image/*"
          startActivityForResult(intent,200)
      }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    if(requestCode == 200 && resultCode == RESULT_OK){
        val selectImage= data?.data
            dataBinding.ciProfile.setImageURI(selectImage)
        }
    }


    private fun insertData() {
        val userEntity=UserEntity(0,dataBinding.teFullName.text.toString(),
            dataBinding.teLastName.text.toString(),
            dataBinding.rgGender.toString(),
            dataBinding.teBirthdate.text.toString())
        userDatabase=Room.databaseBuilder(this,UserDatabase::class.java,"userdata")
            .allowMainThreadQueries().build()
      userDatabase.userDao().UserInsert(userEntity)
        Toast.makeText(this,"Submit",Toast.LENGTH_LONG).show()
    }

    private fun dateOfBirth() {
        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, day)
            updateDate(myCalendar)
        }
        dataBinding.teBirthdate.setOnClickListener {
            DatePickerDialog(
                this,
                datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun updateDate(myCalendar: Calendar) {
        val myformat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myformat, Locale.UK)
        dataBinding.teBirthdate.setText(sdf.format(myCalendar.time))
    }


}


