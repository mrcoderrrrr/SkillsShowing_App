package com.example.demoapps


import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demoapps.activity.Login
import com.example.demoapps.databinding.ActivitySplashscreenBinding

class SplashScreen : AppCompatActivity() {
    lateinit var dataBinding: ActivitySplashscreenBinding
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
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}