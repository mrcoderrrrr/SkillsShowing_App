package com.example.demoapps.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demoapps.MainActivity
import com.example.demoapps.R
import com.example.demoapps.databinding.ActivityLoginBinding

class login_activity : AppCompatActivity() {
    lateinit var databinding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        //signup
        SignUp()
        //login button
        LoginButton()

    }

    private fun LoginButton() {
        val email = databinding.teEmail.text.toString()
        val password = databinding.tePassword.text.toString()
        databinding.butLogin.setOnClickListener {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Log in Sucessfull", Toast.LENGTH_LONG).show()
                    onBackPressed()
            }
        }

    private fun SignUp() {
        databinding.tvSignupTxt.setOnClickListener {
            val intent = Intent(this, signup::class.java)
            startActivity(intent)
        }
    }


}