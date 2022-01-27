package com.example.demoapps.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.room.Room
import com.example.demoapps.R
import com.example.demoapps.dao.UserDao
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
        DateOfBirth()

        dataBinding.btnSubmit.setOnClickListener{
            InsertData()
        }
    }

    private fun InsertData() {
        val user=UserEntity(0,dataBinding.teFullName.text.toString(),
            dataBinding.teLastName.text.toString(),
            dataBinding.rgGender.toString(),
            dataBinding.teBirthdate.text.toString())
        userDatabase=Room.databaseBuilder(this,UserDatabase::class.java,"UserData")
            .allowMainThreadQueries().build()
      userDatabase.userDao().UserInsert(user)
        Toast.makeText(this,"Submit",Toast.LENGTH_LONG).show()
    }

    private fun DateOfBirth() {

        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
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


