package com.example.demoapps.fragment

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demoapps.R
import com.example.demoapps.databinding.FragmentFirebaseAddUserBinding

import com.example.demoapps.model.FireBaseModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*


class FirebaseAddUserFragment : Fragment() {
    private lateinit var dataBinding: FragmentFirebaseAddUserBinding
    private var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var databaseReference: DatabaseReference? = null
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var firebaseUser:FirebaseUser? = firebaseAuth.currentUser
    private var genderVal = ""
    private var profile: Uri? = null
    val firebaseUserFragment=FirebaseUserFragment()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding= DataBindingUtil.inflate(inflater ,R.layout.fragment_firebase_add_user, container, false)
        val view=dataBinding.root
        setClick()
        return view
    }

    private fun setClick() {
        dateOfBirth()
        profileImage()
        gender()
        dataBinding.btnSubmit.setOnClickListener {
            insertFirebaseData()
            val fragmentManager= requireView().context as AppCompatActivity
            fragmentManager.supportFragmentManager.beginTransaction().replace(R.id.fl_userList,firebaseUserFragment)
                .addToBackStack(null)
                .commit()
        }
            dataBinding.btnUpdate.setOnClickListener{
                updateFirebaseData()
                val fragmentManager= requireView().context as AppCompatActivity
                fragmentManager.supportFragmentManager.beginTransaction().replace(R.id.fl_userList,firebaseUserFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    private fun updateFirebaseData() {
        databaseReference = firebaseDatabase.getReference("UserData").child(firebaseUser!!.uid)
        val hashMap = HashMap<Any, Any>()
        //personal Details
        //personal Details
        hashMap["fname"] = dataBinding.teFullName.text.toString()
        hashMap["lname"] = dataBinding.teLastName.text.toString()
        hashMap["gender"] =genderVal
        hashMap["dob"] = dataBinding.teBirthdate.text.toString()
        hashMap["imagepath"] = profile.toString()
        databaseReference!!.setValue(hashMap)
    }
    private fun dateOfBirth() {
        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, day)
            updateDate(myCalendar)
        }
        dataBinding.teBirthdate.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
    private fun insertFirebaseData() {
        val fireBaseModel = FireBaseModel(dataBinding.teFullName.text.toString(),
            dataBinding.teLastName.text.toString(),
            genderVal,
            dataBinding.teBirthdate.text.toString(), profile.toString()
        )
        databaseReference =
            firebaseDatabase.getReference("UserData").child(firebaseUser!!.uid)
        databaseReference!!.setValue(fireBaseModel)
    }
    private fun updateDate(myCalendar: Calendar) {
        val myformat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myformat, Locale.UK)
        dataBinding.teBirthdate.setText(sdf.format(myCalendar.time))
    }
    private fun profileImage() {
        dataBinding.ciProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 200)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200 && resultCode == AppCompatActivity.RESULT_OK) {
            profile = data?.data
            dataBinding.ciProfile.setImageURI(profile)
        }
    }

    private fun gender() {
        dataBinding.rgGender.setOnCheckedChangeListener({ _, checkedId ->
            when (checkedId) {
                R.id.rbut_male -> genderVal = "Male"
                R.id.rbut_female -> genderVal = "Female"
                else -> genderVal = null.toString()
            }
        })
    }
}