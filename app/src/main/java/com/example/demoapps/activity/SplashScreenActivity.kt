package com.example.demoapps


import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demoapps.activity.MainActivity
import com.example.demoapps.databinding.ActivitySplashscreenBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SplashScreenActivity : AppCompatActivity() {
    lateinit var dataBinding: ActivitySplashscreenBinding
    private  var firebaseAuth:FirebaseAuth= FirebaseAuth.getInstance()
    private  var firebaseUser: FirebaseUser? = firebaseAuth.currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_splashscreen)

        //animate logo
        dataBinding.ivLogo.animate().apply {
            duration = 3000
            rotationYBy(360f)
        }.start()

        //animate logo txt
        val tvAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        dataBinding.tvLogoTxt.startAnimation(tvAnimation)


        //open Mainactivity using handler
        android.os.Handler().postDelayed({
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
        }, 3000)
    }
}