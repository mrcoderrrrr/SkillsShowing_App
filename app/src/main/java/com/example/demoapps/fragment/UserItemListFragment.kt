package com.example.demoapps.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demoapps.R
import com.example.demoapps.database.UserDatabase
import com.example.demoapps.databinding.FragmentUserItemListBinding
import com.example.demoapps.entity.UserEntity
import com.squareup.picasso.Picasso


class UserItemListFragment : Fragment() {
    private lateinit var dataBinding: FragmentUserItemListBinding
    private var userId : Int?= null
    val addUserFragment=AddUserFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId= arguments?.getInt("userId",0)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding= DataBindingUtil.inflate(inflater ,R.layout.fragment_user_item_list, container, false)
        val view=dataBinding.root

        Log.d("UserId",userId.toString())
        setClick()
        return view
    }

    private fun setClick() {
        userDataView()
        dataBinding.btnUpdate.setOnClickListener {
            updateData(it)
        }
        dataBinding.btnDelete.setOnClickListener{
            deleteData(it)
        }
    }

    private fun deleteData(view: View) {
        val userEntity=UserEntity(userId!!, dataBinding.tvName.toString(),
            dataBinding.tvName.toString()
        ,dataBinding.tvGender.toString(),dataBinding.tvDob.toString(),dataBinding.cvProfile.toString())
        UserDatabase.getInstance(requireContext())?.userDao()!!.userDeleteData(userEntity)
        val fragmentManager=view.context as AppCompatActivity
        fragmentManager.supportFragmentManager.beginTransaction().replace(R.id.fl_userList,UserItemListFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun userDataView() {
        val user= UserDatabase.getInstance(requireContext())?.userDao()!!.userItemData(userId!!)
        Picasso.get()
            .load(user.imagepath)
            .resize(200,200)
            .centerCrop()
            .into(dataBinding.cvProfile)
        dataBinding.tvName.setText(user.fname+" "+user.lname)
        dataBinding.tvGender.setText(user.gender)
        dataBinding.tvDob.setText(user.dob)
    }

    private fun updateData(view: View) {
        addUserFragment.arguments=arguments
        val fragmentManager=view.context as AppCompatActivity
        fragmentManager.supportFragmentManager.beginTransaction().replace(R.id.fl_userList,addUserFragment)
            .addToBackStack(null)
            .commit()

    }



}