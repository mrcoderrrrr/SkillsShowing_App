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
import com.squareup.picasso.Picasso


class UserItemList : Fragment() {
    private lateinit var dataBinding: FragmentUserItemListBinding
    private var userId: Int? =null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding= DataBindingUtil.inflate(inflater ,R.layout.fragment_user_item_list, container, false)
        val view=dataBinding.root
        val bundle=Bundle()
        val userId=bundle.getInt("userId",0)
        Log.d("userId",userId.toString())
        setClick(userId)
        return view
    }

    private fun setClick(userId: Int) {
        userDataView(userId)
        dataBinding.btnUpdate.setOnClickListener {
            updateData(it)
        }
    }

    private fun userDataView(userId: Int) {
        val user= UserDatabase.getInstance(requireContext())?.userDao()!!.userItemData(userId)
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
        val fragmentManager=view.context as AppCompatActivity
        fragmentManager.supportFragmentManager.beginTransaction().replace(R.id.fl_userList,AddUser())
            .addToBackStack(null)
            .commit()

    }



}