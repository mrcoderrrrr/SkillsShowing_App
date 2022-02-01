package com.example.demoapps.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.demoapps.R
import com.example.demoapps.adapter.RecyclerViewAdapter
import com.example.demoapps.database.UserDatabase
import com.example.demoapps.databinding.ActivityMainBinding
import com.example.demoapps.entity.UserEntity

class MainActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityMainBinding
    private lateinit var toogle: ActionBarDrawerToggle
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private var adpater: RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>? = null
    private lateinit var userEntity:List<UserEntity>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        sharedPreferences = getSharedPreferences("SignUpData", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        recyclerView()
        setClick()
    }

    private fun recyclerView() {
        userEntity = UserDatabase.getInstance(this)?.userDao()!!.userViewData()
        Log.d("UserVal",userEntity.toString())
        dataBinding.rcvUserList.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            dataBinding.rcvUserList.layoutManager = layoutManager
            adpater = RecyclerViewAdapter(context,userEntity)
            dataBinding.rcvUserList.adapter = adpater
        }

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
            R.id.nav_logout -> {
                btnlogout()
                true
            }
            else -> {
                super.onContextItemSelected(item)
                true
            }
        }
    }

    private fun btnlogout() {
        editor.remove("userLogin")
        editor.commit()
        finish()
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        finish()
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