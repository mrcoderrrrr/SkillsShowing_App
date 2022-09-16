package com.example.demoapps.fragment.roomDatabase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapps.R
import com.example.demoapps.activity.MainActivity
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
    ): View {
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
        onBackPressed()
    }

    private fun onBackPressed() {
        val backPressedCallback=object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val intent= Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(backPressedCallback)
    }

    private fun recyclerView() {
    userEntity = UserDatabase.getInstance(requireContext())?.userDao()!!.userViewData() as ArrayList<UserEntity>
    Log.d("UserVal", userEntity.toString())
    dataBinding.rcvUserList.apply {
        layoutManager = LinearLayoutManager(requireContext())
        dataBinding.rcvUserList.layoutManager = layoutManager
        adpater = RecyclerViewAdapter(context, userEntity)
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