package com.example.demoapps.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demoapps.R
import com.example.demoapps.databinding.ActivityMainBinding
import com.example.demoapps.fragment.AddUserFragment
import com.example.demoapps.fragment.FileOperationFragment
import com.example.demoapps.fragment.UserListFragment

class MainActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityMainBinding
    private lateinit var toogle: ActionBarDrawerToggle
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_userList, UserListFragment())
            .commit()
        sharedPreferences = getSharedPreferences("SignUpData", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        setClick()
    }


    private fun setClick() {
        //Navigation menu
        navMenu()
        //Floating Button
        floatbtn()
        //menubar


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_file_operation -> {
                val fragmentmanager = supportFragmentManager
                val fragmentTransaction = fragmentmanager.beginTransaction()
                fragmentTransaction.replace(R.id.fl_userList, FileOperationFragment())
                    .commit()
                Toast.makeText(this, "Hii", Toast.LENGTH_LONG).show()
                true
            }
            R.id.nav_logout -> {
                btnlogout()
                true
            }
            else -> {
                super.onContextItemSelected(item)
            }
        }
    }

    private fun btnlogout() {
        editor.remove("UserLogin")
        editor.commit()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }


    private fun floatbtn() {
        dataBinding.ivAdd.setOnClickListener {
            val fragmentmanager = supportFragmentManager
            val fragmentTransaction = fragmentmanager.beginTransaction()
            fragmentTransaction.replace(R.id.fl_userList, AddUserFragment())
                .commit()
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