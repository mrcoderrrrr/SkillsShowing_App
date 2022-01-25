package com.example.demoapps.activity

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demoapps.R
import com.example.demoapps.databinding.ActivitySignupBinding
import java.text.SimpleDateFormat
import java.util.*

class SignUp : AppCompatActivity() {
    private lateinit var dataBinding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        setClick()
    }

    private fun setSharePreference() {
        val sharedPreference = getSharedPreferences("SignUpData", Context.MODE_PRIVATE)
        val editor = sharedPreference.edit()
        editor.putString("Name", dataBinding.teName.text.toString())
        editor.putString("Email", dataBinding.teEmail.text.toString())
        editor.putString("Password", dataBinding.tePassword.text.toString())
        editor.putString("ConfirmPassword", dataBinding.teConfirmPassword.text.toString())
        Log.d("ShareValue", dataBinding.teName.text.toString() + dataBinding.teEmail.text.toString() + dataBinding.tePassword.toString() + dataBinding.teConfirmPassword.text.toString())
        editor.apply()
        //Gender Data

        Log.d("Data", sharedPreference.getString("Email","").toString())

        setLoginScreen()

    }

    private fun setLoginScreen() {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
    }

    private fun setSignUpData() {
        DatePicker()
    }

    private fun DatePicker() {
        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, day)
            updateDate(myCalendar)
        }
        dataBinding.tlBirthdate.setOnClickListener {
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

    private fun setClick() {
        dataBinding.btnSignup.setOnClickListener {
            if (validate()) {
                setSharePreference()
                setSignUpData()
            }
        }
    }

    private fun validate(): Boolean {
            if (dataBinding.teName.text.toString() == null ) {
                dataBinding.teName.setError("Name is Mandatory")
            } else if (dataBinding.teEmail.text.toString() == null) {
                dataBinding.teEmail.setError("Email is Mandatory")
            } else if (dataBinding.teBirthdate.text.toString() == null) {
                dataBinding.teBirthdate.setError("Birthdate is Mandatory")
            } else if (dataBinding.tePassword.text.toString() == null) {
                dataBinding.tePassword.setError("Password is Mandatory")
            } else if (dataBinding.teConfirmPassword.text.toString() == null) {
                dataBinding.teConfirmPassword.setError("Password is Mandatory")
            } else if (!dataBinding.rbutMale.isChecked && !dataBinding.rbutFemale.isChecked) {
                dataBinding.tvGenderTxt.setError("Gender selection are mandatory")
            } else {
                Toast.makeText(this, "Details Are Submitted", Toast.LENGTH_LONG).show()
            }
            return true
    }


}