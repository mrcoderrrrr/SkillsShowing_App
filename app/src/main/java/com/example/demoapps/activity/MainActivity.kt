package com.example.demoapps.activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demoapps.R
import com.example.demoapps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityMainBinding
    private lateinit var toogle: ActionBarDrawerToggle
    private var clicked = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding =DataBindingUtil.setContentView(this, R.layout.activity_main)
        setClick()
    }

    private fun setClick() {
        //Navigation menu
         navMenu()
        //Floating Button
        floatbtn()

    }

    private fun floatbtn() {
        dataBinding.ivAdd.setOnClickListener {
            onAddButtonClick()
        }
        dataBinding.ivAdduser.setOnClickListener {
            val intent=Intent(this,AddUser::class.java)
            startActivity(intent)
            Toast.makeText(this, "Add User", Toast.LENGTH_LONG).show()
        }
        dataBinding.ivUserList.setOnClickListener {
            Toast.makeText(this, "User List", Toast.LENGTH_LONG).show()
        }
    }

    private fun onAddButtonClick() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            dataBinding.ivAdduser.startAnimation(
                AnimationUtils.loadAnimation(
                    this,
                    R.anim.from_bottom
                )
            )
            dataBinding.ivUserList.startAnimation(
                AnimationUtils.loadAnimation(
                    this,
                    R.anim.from_bottom
                )
            )
            dataBinding.ivAdd.startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotate_open))
        }
        else{
            dataBinding.ivAdduser.startAnimation( AnimationUtils.loadAnimation(this,R.anim.to_bottom))
            dataBinding.ivUserList.startAnimation( AnimationUtils.loadAnimation(this,R.anim.to_bottom))
            dataBinding.ivAdd.startAnimation( AnimationUtils.loadAnimation(this,R.anim.rotate_close))
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            dataBinding.ivAdduser.visibility = View.VISIBLE
            dataBinding.ivUserList.visibility=View.VISIBLE
        }
        else{
            dataBinding.ivAdduser.visibility = View.INVISIBLE
            dataBinding.ivUserList.visibility=View.INVISIBLE
        }
    }

    private fun navMenu() {
        toogle = ActionBarDrawerToggle(this, dataBinding.dlmain, R.string.open, R.string.close)
        dataBinding.dlmain.addDrawerListener(toogle)
        toogle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dataBinding.navMenu.setNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.nav_home -> Toast.makeText(this, "Home", Toast.LENGTH_LONG).show()
                R.id.nav_contact -> Toast.makeText(this, "About Us", Toast.LENGTH_LONG).show()
                R.id.nav_about -> Toast.makeText(this, "Contact Us", Toast.LENGTH_LONG).show()

            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toogle.onOptionsItemSelected(item)){

            return true
        }
            return super.onOptionsItemSelected(item)
    }
}