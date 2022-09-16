package com.example.demoapps.fragment.roomDatabase

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.demoapps.R
import com.example.demoapps.database.UserDatabase
import com.example.demoapps.databinding.FragmentAddUserBinding
import com.example.demoapps.entity.UserEntity
import java.text.SimpleDateFormat
import java.util.*


class AddUserFragment : Fragment() {
    private lateinit var dataBinding: FragmentAddUserBinding
    private var genderVal = ""
    private var userId: Int? =null
    private var profile: Uri? = null
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userId = requireArguments().getInt("userId", 0)
        Log.d("UserID", userId.toString())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_user, container, false)
        val view = dataBinding.root
        setClick()
        return view
    }

    private fun setClick() {
        dateOfBirth()
        profileImage()
        gender()
        dataBinding.btnSubmit.setOnClickListener {
            if (userId!! >= 1) {
                updateUserData()
                val fragmentmanager = it.context as AppCompatActivity
                fragmentmanager.supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_userList, UserListFragment())
                    .commit()
            } else if (userId == null || userId == 0) {
                    insertSharedPrefrenceData()
                    val fragmentmanager = it.context as AppCompatActivity
                    fragmentmanager.supportFragmentManager.beginTransaction()
                        .replace(R.id.fl_userList, UserListFragment())
                        .commit()
            }
        }
    }


    private fun updateUserData() {
        val userEntity = UserEntity(
            userId!!, dataBinding.teFullName.text.toString(),
            dataBinding.teLastName.text.toString(),
            genderVal,
            dataBinding.teBirthdate.text.toString(), profile.toString()
        )
        UserDatabase.getInstance(requireContext())?.userDao()!!.userUpdate(userEntity)
    }

    private fun insertSharedPrefrenceData() {
        val userEntity = UserEntity(
            0, dataBinding.teFullName.text.toString(),
            dataBinding.teLastName.text.toString(),
            genderVal,
            dataBinding.teBirthdate.text.toString(), profile.toString()
        )
        UserDatabase.getInstance(requireContext())?.userDao()!!.userInsert(userEntity)
        Toast.makeText(requireContext(), "Submit", Toast.LENGTH_LONG).show()
    }

    private fun gender() {
        dataBinding.rgGender.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbut_male -> genderVal = "Male"
                R.id.rbut_female -> genderVal = "Female"
                else -> genderVal = null.toString()
            }
        }
    }

    private fun profileImage() {
        getProfile()
        dataBinding.ciProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            resultLauncher.launch(intent)
//            startActivityForResult(intent, 200)
        }
    }

    private fun getProfile() {
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback<ActivityResult>() {
                var data: Intent = it.data!!
                if (data != null) {
                    profile = data.data
                    dataBinding.ciProfile.setImageURI(profile)
                }
            }
        )
    }
    /* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)
         if (requestCode == 200 && resultCode == AppCompatActivity.RESULT_OK) {
             profile = data?.data
             dataBinding.ciProfile.setImageURI(profile)
         }
     }*/

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

    private fun updateDate(myCalendar: Calendar) {
        val myformat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myformat, Locale.UK)
        dataBinding.teBirthdate.setText(sdf.format(myCalendar.time))
    }
}