package com.example.demoapps.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demoapps.R
import com.example.demoapps.databinding.ActivityMainBinding
import com.example.demoapps.fragment.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.intellij.lang.annotations.Language
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityMainBinding
    private lateinit var toogle: ActionBarDrawerToggle
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var firebaseUser: FirebaseUser? = firebaseAuth.currentUser
    lateinit var dialog: Dialog
    lateinit var locale: Locale
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_userList, UserListFragment())
            .commit()
        sharedPreferences = getSharedPreferences("SignUpData", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        dialog = Dialog(this)
        setLanguage(language = String())
        setClick()
    }

    private fun setClick() {
        //Navigation menu
        navMenu()
        checkUser()
    }

    private fun setLanguage(language:String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = getResources()
        val configuration = resources.getConfiguration()
        configuration.locale = locale
        resources.updateConfiguration(configuration, resources.getDisplayMetrics())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    private fun checkUser() {
        if (firebaseUser != null) {
            Log.d("user", firebaseUser.toString())
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
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

                }
                R.id.nav_file_operation -> {
                    val fragmentmanager = supportFragmentManager
                    val fragmentTransaction = fragmentmanager.beginTransaction()
                    fragmentTransaction.replace(R.id.fl_userList, FileOperationFragment())
                        .commit()
                }
                R.id.nav_social -> {
                    val fragmentmanager = supportFragmentManager
                    val fragmentTransaction = fragmentmanager.beginTransaction()
                    fragmentTransaction.replace(R.id.fl_userList, SocialLoginFragment())
                        .commit()
                }
                R.id.nav_firebase -> {
                    val fragmentmanager = supportFragmentManager
                    val fragmentTransaction = fragmentmanager.beginTransaction()
                    fragmentTransaction.replace(R.id.fl_userList, FirebaseUserFragment())
                        .commit()
                }
                R.id.nav_bottom_menu -> {
                    val fragmentmanager = supportFragmentManager
                    val fragmentTransaction = fragmentmanager.beginTransaction()
                    fragmentTransaction.replace(R.id.fl_userList, BottomFragment())
                        .commit()
                }
                R.id.nav_notify -> {
                    val fragmentmanager = supportFragmentManager
                    val fragmentTransaction = fragmentmanager.beginTransaction()
                    fragmentTransaction.replace(R.id.fl_userList, NotificationFragment())
                        .commit()
                }
                R.id.nav_google_map -> {
                    val fragmentmanager = supportFragmentManager
                    val fragmentTransaction = fragmentmanager.beginTransaction()
                    fragmentTransaction.replace(R.id.fl_userList, GoogleMapFragment())
                        .commit()
                }
                R.id.nav_api -> {
                    val fragmentmanager = supportFragmentManager
                    val fragmentTransaction = fragmentmanager.beginTransaction()
                    fragmentTransaction.replace(R.id.fl_userList, ApiFragment())
                        .commit()
                }
                R.id.nav_drawing -> {
                    val fragmentmanager = supportFragmentManager
                    val fragmentTransaction = fragmentmanager.beginTransaction()
                    fragmentTransaction.replace(R.id.fl_userList, CustomDrawingFragment())
                        .commit()
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

    override fun onSupportNavigateUp(): Boolean {
        dataBinding.dlmain.openDrawer(dataBinding.navMenu)
        return super.onSupportNavigateUp()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toogle.onOptionsItemSelected(item)){
            return true
        }
        when (item.itemId) {
            R.id.nav_language -> {
                dialogBox()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun dialogBox() {
        dialog.setContentView(R.layout.language_cardview)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val confirm = dialog.findViewById<Button>(R.id.btn_confirm)
        val english = dialog.findViewById<RadioButton>(R.id.rb_english)
        val hindi = dialog.findViewById<RadioButton>(R.id.rb_hindi)
        val gujrati = dialog.findViewById<RadioButton>(R.id.rb_gujrati)
        val localLanguage=dialog.findViewById<RadioGroup>(R.id.rg_language)
        val selectId=localLanguage.checkedRadioButtonId
        confirm.setOnClickListener {
            if(english.isChecked == true){
                setLanguage("en")
                val intent=Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this,"English",Toast.LENGTH_LONG).show()
            }
            else if(hindi.isChecked == true){
                setLanguage("hi")
                val intent=Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this,"Hindi",Toast.LENGTH_LONG).show()
            }
            else if(gujrati.isChecked == true){
                setLanguage("gu")
                val intent=Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
                Toast.makeText(this,"Gurjarti",Toast.LENGTH_LONG).show()
            }
        }
        dialog.show()
    }
}