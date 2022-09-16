package com.example.demoapps.activity

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demoapps.R
import com.example.demoapps.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivitySignupBinding
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var genderVal: Any? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        setClick()
    }
    private fun setClick() {
        //sign up data in firebase and sharedprefrence
        dataBinding.btnSignup.setOnClickListener {
            if (validate()) {
                //sharedprefrence store value
                setSharePreference()
                //firebaseDataStrore()
                firebaseSignUp()
            }
        }
        dateOfBirth()
        gender()
    }
    private fun setSharePreference() {
        if (dataBinding.tePassword.text.toString()
                .equals(dataBinding.teConfirmPassword.text.toString())
        ) {
            val sharedPreference = getSharedPreferences("SignUpData", Context.MODE_PRIVATE)
            val editor = sharedPreference.edit()
            editor.putString("Name", dataBinding.teName.text.toString())
            editor.putString("Email", dataBinding.teEmail.text.toString())
            editor.putString("Gender", genderVal.toString())
            editor.putString("Dob", dataBinding.teBirthdate.text.toString())
            editor.putString("Password", dataBinding.tePassword.text.toString())
            editor.putString("ConfirmPassword", dataBinding.teConfirmPassword.text.toString())
            editor.apply()
            //Gender Data
            Log.d("Data", sharedPreference.getString("Email", "").toString())
            //after data was submitted go to log in activity
            setLoginScreen()
        }
        else{
          Toast.makeText(this,"Password doesn't matched",Toast.LENGTH_SHORT).show()
        }
    }
//after data was submitted go to log in activity
    private fun setLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
//get user birthdate
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
//set date in format
    private fun updateDate(myCalendar: Calendar) {
        val myformat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myformat, Locale.UK)
        dataBinding.teBirthdate.setText(sdf.format(myCalendar.time))
    }


//gender value
    private fun gender() {
        dataBinding.rgGender.setOnCheckedChangeListener({ _, checkedId ->
            when (checkedId) {
                R.id.rbut_male -> genderVal = "Male"
                R.id.rbut_female -> genderVal = "Female"
                else -> genderVal = null.toString()
            }
        })
    }


//firebase sign up data
    private fun firebaseSignUp() {
            if (dataBinding.tePassword.text.toString()
                    .equals(dataBinding.teConfirmPassword.text.toString())
            ) {
                firebaseAuth.createUserWithEmailAndPassword(
                    dataBinding.teEmail.text.toString(),
                    dataBinding.teConfirmPassword.text.toString()
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this, "Invalid", Toast.LENGTH_LONG
                            ).show()
                        }
                    }
        }
    else{
                Toast.makeText(this,"Password doesn't matched",Toast.LENGTH_SHORT).show()
    }
    }

//vaidate user data
    private fun validate(): Boolean {
        if (dataBinding.teName.text.toString().isEmpty()) {
            dataBinding.teName.setError("Name is Mandatory")
            return false
        } else if (dataBinding.teEmail.text.toString().isEmpty()) {
            dataBinding.teEmail.setError("Email is Mandatory")
            return false
        } else if (dataBinding.teBirthdate.text.toString().isEmpty()) {
            dataBinding.teBirthdate.setError("Birthdate is Mandatory")
            return false
        } else if (dataBinding.tePassword.text.toString().isEmpty()) {
            Toast.makeText(this,"Password is Mandatory",Toast.LENGTH_SHORT).show()
        }
        else if (dataBinding.teConfirmPassword.text.toString().isEmpty()) {
            Toast.makeText(this,"Password is Mandatory",Toast.LENGTH_SHORT).show()
        }
        else if (!dataBinding.rbutMale.isChecked && !dataBinding.rbutFemale.isChecked) {
            dataBinding.tvGenderTxt.setError("Gender selection are mandatory")
        } else {
            Toast.makeText(this, "Details Are Submitted", Toast.LENGTH_LONG).show()
        }
        return true
    }


}