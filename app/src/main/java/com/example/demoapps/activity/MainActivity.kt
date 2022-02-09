package com.example.demoapps.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demoapps.R
import com.example.demoapps.SplashScreenActivity
import com.example.demoapps.databinding.ActivityMainBinding
import com.example.demoapps.fragment.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser




class MainActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityMainBinding
    private lateinit var toogle: ActionBarDrawerToggle
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private  var firebaseAuth:FirebaseAuth= FirebaseAuth.getInstance()
    private  var firebaseUser: FirebaseUser? = firebaseAuth.currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_userList, UserListFragment())
            .commit()
        sharedPreferences = getSharedPreferences("SignUpData", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
      if(firebaseUser != null) {

      }else{
          startActivity(Intent(this, LoginActivity::class.java))
          finish()
      }
        setClick()
    }

    private fun setClick() {
        //Navigation menu
        navMenu()

    }


    private fun navMenu() {
        toogle = ActionBarDrawerToggle(this, dataBinding.dlmain, R.string.open, R.string.close)
        dataBinding.dlmain.addDrawerListener(toogle)
        toogle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        dataBinding.navMenu.setNavigationItemSelectedListener {
            when (it.itemId) {

                R.id.nav_home -> {
                    val fragmentmanager = supportFragmentManager
                    val fragmentTransaction = fragmentmanager.beginTransaction()
                    fragmentTransaction.replace(R.id.fl_userList, UserListFragment())
                        .commit()
                    true
                }
                        R.id.nav_file_operation -> {
                    val fragmentmanager = supportFragmentManager
                    val fragmentTransaction = fragmentmanager.beginTransaction()
                    fragmentTransaction.replace(R.id.fl_userList, FileOperationFragment())
                        .commit()
                    true
                }
                R.id.nav_firebase -> {
                    val fragmentmanager = supportFragmentManager
                    val fragmentTransaction = fragmentmanager.beginTransaction()
                    fragmentTransaction.replace(R.id.fl_userList, FirebaseUserFragment())
                        .commit()
                    true
                }
                R.id.nav_bottom_menu -> {
                    val fragmentmanager = supportFragmentManager
                    val fragmentTransaction = fragmentmanager.beginTransaction()
                    fragmentTransaction.replace(R.id.fl_userList, BottomFragment())
                        .commit()
                    true
                }
                R.id.nav_google_map -> {
                    val fragmentmanager = supportFragmentManager
                    val fragmentTransaction = fragmentmanager.beginTransaction()
                    fragmentTransaction.replace(R.id.fl_userList, GoogleMapFragment())
                        .commit()
                    true
                }
                R.id.nav_notify -> {
                    val fragmentmanager = supportFragmentManager
                    val fragmentTransaction = fragmentmanager.beginTransaction()
                    fragmentTransaction.replace(R.id.fl_userList, NotificationFragment())
                        .commit()
                    true
                }
                R.id.nav_logout -> {
                    firebaseAuth.signOut()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }

            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toogle.onOptionsItemSelected(item)) {
            when(item.itemId){
            }
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}