package com.example.demoapps.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapps.R
import com.example.demoapps.adapter.RecyclerViewAdapter
import com.example.demoapps.database.UserDatabase
import com.example.demoapps.databinding.FragmentUserListBinding
import com.example.demoapps.entity.UserEntity


class UserListFragment : Fragment() {
    private lateinit var dataBinding: FragmentUserListBinding
    private var adpater: RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>? = null
    private lateinit var userEntity: ArrayList<UserEntity>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_list, container, false)
        val view = dataBinding.root
            setClick()
        // Inflate the layout for this fragment
        return view
    }

    private fun setClick() {
        recyclerView()
        floatBtn()
    }

fun recyclerView() {
    userEntity = UserDatabase.getInstance(requireContext())?.userDao()!!.userViewData() as ArrayList<UserEntity>
    Log.d("UserVal", userEntity.toString())
    dataBinding.rcvUserList.apply {
        layoutManager = LinearLayoutManager(requireContext())
        dataBinding.rcvUserList.layoutManager = layoutManager
        adpater = RecyclerViewAdapter(context, userEntity as ArrayList<UserEntity>)
        dataBinding.rcvUserList.adapter = adpater
    }
}

private fun floatBtn() {
        dataBinding.btnFloat.setOnClickListener {
            val fragmentmanager = it.context as AppCompatActivity
            fragmentmanager.supportFragmentManager.beginTransaction()
                .replace(R.id.fl_userList, AddUserFragment())
                .commit()
        }
    }

}