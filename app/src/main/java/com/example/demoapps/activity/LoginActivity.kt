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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        sharedPreferences = getSharedPreferences("SignUpData", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.putBoolean("userLogin", true)
        editor.commit()
        editor.apply()
        //OnClick
        setClick()
    }


    private fun setClick() {
        //sign up
        dataBinding.tvSignupTxt.setOnClickListener {
            setSignUp()
        }
        //log in
        dataBinding.butLogin.setOnClickListener {
            if (validate()) {
                //Firebase Login
                firebaseLogin()
                return@setOnClickListener
            }
        }
    }

    //Firebase User Login
    private fun firebaseLogin() {
        if (dataBinding.teEmail.text.toString()
                .isNotEmpty() && dataBinding.tePassword.text.toString()
                .isNotEmpty()
        ) {
            firebaseAuth.signInWithEmailAndPassword(
                dataBinding.teEmail.text.toString(),
                dataBinding.tePassword.text.toString()
            )
                .addOnCompleteListener(this, OnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                })
        }
    }
//chech value is null or not
    private fun validate(): Boolean {
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
            sharedPreferences = getSharedPreferences("SignUpData", Context.MODE_PRIVATE)
            if (dataBinding.teEmail.text.toString() == (sharedPreferences.getString(
                    "Email",
                    " "
                )) && dataBinding.tePassword.text.toString() == (sharedPreferences.getString(
                    "Password",
                    ""
                ))
            ) {
                setHome()
            }
        }
        return true
    }
        // after log in go to new activity
    private fun setHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
        Toast.makeText(this, "Log in Successful", Toast.LENGTH_LONG).show()
    }
//sign up text to go sign up page
    private fun setSignUp() {
        dataBinding.tvSignupTxt.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }


}