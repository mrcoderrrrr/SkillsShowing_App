package com.example.demoapps.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
        val email = databinding.teEmail.text
        val password = databinding.tePassword.text
        databinding.butLogin.setOnClickListener {
            if (email!!.isEmpty()){
                Log.d("Emailtxt", email.toString())
                Log.d("Emaildata",databinding.teEmail.text.toString())
             databinding.teEmail.setError("Username is mandatory")
            }
            else if (password!!.isEmpty()) {
                databinding.tePassword.setError("Password is manadatory")
            }
            else {
                if (email.toString().equals("admin") && password.toString().equals("admin")) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this, "Log in Sucessfull", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this, "Log in Faild", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun SignUp() {
        databinding.tvSignupTxt.setOnClickListener {
            val intent = Intent(this, signup::class.java)
            startActivity(intent)
        }
    }


}