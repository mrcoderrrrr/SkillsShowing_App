package com.example.demoapps.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.demoapps.R
import com.example.demoapps.database.UserDatabase
import com.example.demoapps.databinding.FragmentUserItemListBinding
import com.example.demoapps.databinding.FragmentUserListBinding
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
        // Inflate the layout for this fragment
        val bundle=Bundle()
        val userId=bundle.getInt("userId")
        val user= UserDatabase.getInstance(requireContext())?.userDao()!!.userItemData(userId)
        Picasso.get()
            .load(user.imagepath)
            .resize(200,200)
            .centerCrop()
            .into(dataBinding.cvProfile)
        dataBinding.tvName.setText(user.fname+" "+user.lname)
        dataBinding.tvGender.setText(user.gender)
        dataBinding.tvDob.setText(user.dob)
        return view
    }


}