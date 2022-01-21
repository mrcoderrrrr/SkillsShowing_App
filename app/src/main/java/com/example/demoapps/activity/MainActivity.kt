package com.example.demoapps.activity

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
    lateinit var toogle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

       setClick()
    }

    private fun setClick() {
        //Navigation menu
       // navMenu()
        //Floating Button
        floatbtn()

    }

    private fun floatbtn() {

    }

//    private fun navMenu() {
//        toogle = ActionBarDrawerToggle(this, dataBinding.dlMain, R.string.open, R.string.close)
//        dataBinding.dlMain.addDrawerListener(toogle)
//        toogle.syncState()
//
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//
//        dataBinding.nvMenu.setNavigationItemSelectedListener {
//            when (it.itemId) {
//
//                R.id.nav_home -> Toast.makeText(this, "Home", Toast.LENGTH_LONG).show()
//                R.id.nav_contact -> Toast.makeText(this, "About Us", Toast.LENGTH_LONG).show()
//                R.id.nav_about -> Toast.makeText(this, "Contact Us", Toast.LENGTH_LONG).show()
//
//            }
//            true
//        }
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if(toogle.onOptionsItemSelected(item)){
//
//            return true
//        }
//            return super.onOptionsItemSelected(item)
//    }
}