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
        val email = databinding.teEmail.text.toString()
        val password = databinding.tePassword.text.toString()
        databinding.butLogin.setOnClickListener {
            if (email.toString().isEmpty()){
                Log.d("Emailtxt",email)
                Log.d("Emaildata",databinding.teEmail.text.toString())
             databinding.teEmail.setError("Username is mandatory")
            }
            else if (password.isEmpty()) {
                databinding.tePassword.setError("Password is manadatory")
            }
            else {
                Log.d("Emailtxt1",email)
                Log.d("Emaildata1",databinding.teEmail.text.toString())
                Log.d("Passtxt1",password)
                Log.d("Passdata1",databinding.tePassword.text.toString())
                if (email.equals("admin") && password.equals("admin")) {
                    Log.d("Emailtxt",email)
                    Log.d("Emaildata",databinding.teEmail.text.toString())
                    Log.d("Passtxt",password)
                    Log.d("Passdata",databinding.tePassword.text.toString())
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
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