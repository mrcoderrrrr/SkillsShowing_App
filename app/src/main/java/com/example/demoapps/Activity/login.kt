package com.example.demoapps.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.demoapps.MainActivity
import com.example.demoapps.R
import com.example.demoapps.databinding.ActivityLoginBinding

class login_activity : AppCompatActivity() {
    lateinit var databinding :ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding=DataBindingUtil.setContentView(this,R.layout.activity_login)

        //signup
        SignUp()
        //login button
        LoginButton()

    }

    private fun LoginButton() {
        val email=databinding.teEmail.text.toString()
        val password=databinding.tePassword.text.toString()
        databinding.butLogin.setOnClickListener {
            if(email.isEmpty() || password.isEmpty()) {
                if (email.equals("admin") || password.equals("admin")) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Log in Sucessfull", Toast.LENGTH_LONG).show()
                    finish()
                } else {
                    Toast.makeText(this, "Inncorrect Passwod", Toast.LENGTH_LONG).show()
                }
            }
            else {
                Toast.makeText(this, "Enter ", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun SignUp() {
        databinding.tvSignupTxt.setOnClickListener {
            val intent=Intent(this,signup::class.java)
            startActivity(intent)
        }
    }
}