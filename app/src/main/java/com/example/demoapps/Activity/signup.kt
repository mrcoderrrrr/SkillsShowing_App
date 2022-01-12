package com.example.demoapps.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.demoapps.R
import com.example.demoapps.databinding.ActivityLoginBinding
import com.example.demoapps.databinding.ActivitySignupBinding

class signup : AppCompatActivity() {
    lateinit var databinding:ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding=DataBindingUtil.setContentView(this,R.layout.activity_signup)

        //Enter Details
        SignUpDetails()
    }

    private fun SignUpDetails() {
        databinding.butSignup.setOnClickListener {
            val name=databinding.teName.text.toString()
            val email=databinding.teEmail.text.toString()
            val dob=databinding.teBirthdate.text.toString()
            val password=databinding.tePassword.text.toString()
            val confirm_password=databinding.teConfirmPassword.text.toString()
            val male=databinding.rbutMale
            val female=databinding.rbutFemale
            if(name.isEmpty() || email.isEmpty() || dob.isEmpty() || password.isEmpty() || confirm_password.isEmpty()){
               databinding.teEmail.setError("Email is Mandatory")
                databinding.tePassword.setError("Password is Mandatory")
                databinding.teConfirmPassword.setError("Passworod is Mandatory")
                Toast.makeText(this,"Fill the Detalis",Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this,"Detalis Are Submitted",Toast.LENGTH_LONG).show()
            }

        }
    }
}