package com.example.demoapps.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapps.R
import com.example.demoapps.adapter.FirebaseAdapter
import com.example.demoapps.databinding.FragmentFirebaseUserBinding
import com.example.demoapps.model.FireBaseModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlin.collections.ArrayList


class FirebaseUserFragment : Fragment() {
    private lateinit var dataBinding: FragmentFirebaseUserBinding
    private var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var databaseReference: DatabaseReference? = null
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var firebaseUser: FirebaseUser? = firebaseAuth.currentUser
    private  var fireBaseModel:FireBaseModel? =null
    private  var fireBaseData:ArrayList<FireBaseModel>? =null
    private var firebaseAdapter: RecyclerView.Adapter<FirebaseAdapter.ViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_firebase_user, container, false)
        val view = dataBinding.root
        setClick()
        return view
    }

    private fun setClick() {
        recyclerView()
        floatbtn()
    }

    private fun floatbtn() {
        var firebaseAddUserFragment:FirebaseAddUserFragment=FirebaseAddUserFragment()
        dataBinding.btnFloat.setOnClickListener{
        val fragmentManager= it.context as AppCompatActivity
        fragmentManager.supportFragmentManager.beginTransaction().replace(R.id.fl_userList,firebaseAddUserFragment)
            .addToBackStack(null)
            .commit()
        }
    }

    private fun recyclerView() {

        databaseReference = firebaseDatabase.getReference("UserData").child(firebaseUser!!.uid)
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                        fireBaseData= ArrayList<FireBaseModel>()
                        fireBaseModel = FireBaseModel(
                            snapshot.child("fname").value as String,
                            snapshot.child("lname").value  as String,
                            snapshot.child("gender").value  as String,
                            snapshot.child("dob").value  as String,
                            snapshot.child("imagepath").getValue().toString())
                        fireBaseData!!.add(fireBaseModel!!)
                    dataBinding.rcvUserList.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        dataBinding.rcvUserList.layoutManager = layoutManager
                        firebaseAdapter = FirebaseAdapter(context, fireBaseData!!)
                        dataBinding.rcvUserList.adapter =firebaseAdapter
                        (firebaseAdapter as FirebaseAdapter).notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}


