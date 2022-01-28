package com.example.demoapps.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demoapps.R
import com.example.demoapps.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var dataBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        val sharedPreferences = getSharedPreferences("SignUpData", Context.MODE_PRIVATE)
        //OnClick
        setClick(sharedPreferences)
    }

    private fun setClick(sharedPreferences: SharedPreferences) {
        dataBinding.butLogin.setOnClickListener {
            if (validate(sharedPreferences)) {
                return@setOnClickListener
            }
        }
        dataBinding.tvSignupTxt.setOnClickListener {
            setSignUp()
        }
    }

    private fun validate(sharedPreferences: SharedPreferences): Boolean {
        if (dataBinding.teEmail.text.toString().isEmpty()) {
            dataBinding.teEmail.setError("Username is mandatory")
        } else if (dataBinding.tePassword.text.toString().isEmpty()) {
            dataBinding.tePassword.setError("Password is mandatory")
        } else if (dataBinding.teEmail.text.toString()
                .isEmpty() || dataBinding.tePassword.text.toString().isEmpty()
        ) {
            dataBinding.teEmail.setError("Username is mandatory")
            dataBinding.tePassword.setError("Password is mandatory")
        } else {
            if (dataBinding.teEmail.text.toString() == (sharedPreferences.getString("Email",
                    " ")) && dataBinding.tePassword.text.toString() == (sharedPreferences.getString(
                    "Password",
                    ""))
            ) {
                setHome()
            } else {
                Toast.makeText(this, "Log in failed", Toast.LENGTH_LONG).show()
            }
        }
        return true
    }

    private fun setHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        Toast.makeText(this, "Log in Successful", Toast.LENGTH_LONG).show()
    }

    private fun setSignUp() {
        dataBinding.tvSignupTxt.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }


}