package com.example.demoapps.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demoapps.R
import com.example.demoapps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityMainBinding
    private lateinit var toogle: ActionBarDrawerToggle
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        sharedPreferences = getSharedPreferences("SignUpData", Context.MODE_PRIVATE)
        editor=sharedPreferences.edit()
        setClick()
    }

    private fun setClick() {
        //Navigation menu
        navMenu()
        //Floating Button
        floatbtn()
       //btnlogout()
    }

    private fun btnlogout() {
//        dataBinding.btnLogout.setOnClickListener {
//            editor.remove("userLogin")
//            editor.commit()
//            finish()
//            val intent = Intent(this, Login::class.java)
//            startActivity(intent)
//            finish()
//        }
        }

    private fun floatbtn() {
        dataBinding.ivAdd.setOnClickListener {

            val intent = Intent(this, AddUser::class.java)
            startActivity(intent)
            Toast.makeText(this, "Add User", Toast.LENGTH_LONG).show()
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
        if (toogle.onOptionsItemSelected(item)) {

            return true
        }
        return super.onOptionsItemSelected(item)
    }
}