package com.example.demoapps.fragment

import android.database.DatabaseUtils
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapps.R
import com.example.demoapps.adapter.RecyclerViewAdapter
import com.example.demoapps.database.UserDatabase
import com.example.demoapps.databinding.ActivityMainBinding
import com.example.demoapps.databinding.FragmentUserListBinding
import com.example.demoapps.entity.UserEntity


class UserListFragment : Fragment() {
    private lateinit var dataBinding: FragmentUserListBinding
    private var adpater: RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>? = null
    private lateinit var userEntity:List<UserEntity>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding=DataBindingUtil.inflate(inflater ,R.layout.fragment_user_list, container, false)
       val view=dataBinding.root
        userEntity = UserDatabase.getInstance(requireContext())?.userDao()!!.userViewData()
        Log.d("UserVal",userEntity.toString())
        dataBinding.rcvUserList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            dataBinding.rcvUserList.layoutManager = layoutManager
            adpater = RecyclerViewAdapter(context,userEntity)
            dataBinding.rcvUserList.adapter = adpater
        }
        // Inflate the layout for this fragment
        return view
    }

}