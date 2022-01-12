package com.example.demoapps


import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import com.example.demoapps.Activity.login_activity
import com.example.demoapps.R
import com.example.demoapps.databinding.ActivitySplashScreenBinding

class splash_screen : AppCompatActivity() {
    lateinit var databinding : ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databinding=DataBindingUtil.setContentView(this,R.layout.activity_splash_screen)
        //animate logo
        databinding.ivLogo.animate().apply {
            duration = 3000
            rotationYBy(360f)
        }.start()

        //animate logo txt
        val tv_animation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        databinding.tvLogoTxt.startAnimation(tv_animation)


        //open mainactivity using handler
        android.os.Handler().postDelayed({
            val intent = Intent(this, login_activity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}