package com.example.demoapps.Activity

import android.os.Bundle
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demoapps.R
import com.example.demoapps.databinding.ActivitySignupBinding

class signup : AppCompatActivity() {
    lateinit var databinding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding = DataBindingUtil.setContentView(this, R.layout.activity_signup)

        //error message
        errormsg()
    }

    //Error Message
    private fun errormsg() {
        databinding.butSignup.setOnClickListener {
            val name = databinding.teName.text.toString()
            val email = databinding.teEmail.text.toString()
            val dob = databinding.teBirthdate.text.toString()
            val password = databinding.tePassword.text.toString()
            val confirm_password = databinding.teConfirmPassword.text.toString()
            val male = databinding.rbutMale
            val female = databinding.rbutFemale

            if (name.isEmpty()) {
                databinding.teName.setError("Name is Mandatory")
            } else if (email.isEmpty()) {
                databinding.teEmail.setError("Email is Mandatory")
            } else if (dob.isEmpty()) {
                databinding.teBirthdate.setError("Birthdate is Mandatory")
            } else if (password.isEmpty()) {
                databinding.tePassword.setError("Password is Mandatory")
            } else if (confirm_password.isEmpty()) {
                databinding.teConfirmPassword.setError("Passworod is Mandatory")
            } else {
                Toast.makeText(this, "Detalis Are Submitted", Toast.LENGTH_LONG).show()
            }
            //Gender value
            gender(male,female)
        }
    }

    //Gender
    private fun gender(male: RadioButton, female: RadioButton) {
        if (male.isChecked) {
            var Male = "Male"
        } else if (female.isChecked) {
            var Female = "Female"
        } else {
            if (!male.isChecked && !female.isChecked) {
                databinding.tvGenderTxt.setError("Gender selection are mandatory")
            }
        }
    }
}