package com.example.demoapps.Activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demoapps.R
import com.example.demoapps.databinding.ActivitySignupBinding

class SignUp : AppCompatActivity() {
    lateinit var dataBinding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

        setClick()
    }

    private fun setSharePreference() {
        val SharedPreference = getSharedPreferences("SignUpData", Context.MODE_PRIVATE)
        val editor = SharedPreference.edit()
        editor.putString("Name", dataBinding.teName.text.toString())
        editor.putString("Email", dataBinding.teEmail.text.toString())
        editor.putString("Password", dataBinding.tePassword.text.toString())
        editor.putString("ConfirmPassword", dataBinding.teConfirmPassword.text.toString())
        Log.d("ShareValue", dataBinding.teName.text.toString() + dataBinding.teEmail.text.toString() + dataBinding.tePassword.toString() + dataBinding.teConfirmPassword.text.toString())
        editor.apply()
        editor.commit()
        //Gender Data
        setSignUpData()
        Log.d("Data", SharedPreference.getString("Email","").toString())

        setLoginScreen()

    }

    private fun setLoginScreen() {
        val intent = Intent(this, com.example.demoapps.Activity.login_activity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setSignUpData() {

    }

    private fun setClick() {
        dataBinding.butSignup.setOnClickListener {
            if (validate()) {
                setSharePreference()
            }
        }
    }

    private fun validate(): Boolean {
        if (dataBinding.teName.text.toString().isEmpty()) {
            dataBinding.teName.setError("Name is Mandatory")
        } else if (dataBinding.teEmail.text.toString().isEmpty()) {
            dataBinding.teEmail.setError("Email is Mandatory")
        } else if (dataBinding.teBirthdate.text.toString().isEmpty()) {
            dataBinding.teBirthdate.setError("Birthdate is Mandatory")
        } else if (dataBinding.tePassword.text.toString().isEmpty()) {
            dataBinding.tePassword.setError("Password is Mandatory")
        } else if (dataBinding.teConfirmPassword.text.toString().isEmpty()) {
            dataBinding.teConfirmPassword.setError("Passworod is Mandatory")
        } else if (!dataBinding.rbutMale.isChecked && !dataBinding.rbutFemale.isChecked) {
            dataBinding.tvGenderTxt.setError("Gender selection are mandatory")
        } else {
            Toast.makeText(this, "Detalis Are Submitted", Toast.LENGTH_LONG).show()
        }
        return true
    }
}