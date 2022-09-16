package com.example.demoapps.fragment.firebase

import android.content.Intent
import android.os.Bundle
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
import com.example.demoapps.adapter.FirebaseAdapter
import com.example.demoapps.databinding.FragmentFirebaseUserBinding
import com.example.demoapps.model.FireBaseModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


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
    ): View {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_firebase_user, container, false)
        val view = dataBinding.root
        setClick()
        return view
    }

    private fun setClick() {
        recyclerView()
        floatbtn()
        onBackpressed()
    }

    private fun onBackpressed() {
        val backPressedCallback=object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                val intent= Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(backPressedCallback)
    }

    private fun floatbtn() {
        val firebaseAddUserFragment= FirebaseAddUserFragment()
        dataBinding.btnFloat.setOnClickListener{
        val fragmentManager= it.context as AppCompatActivity
        fragmentManager.supportFragmentManager.beginTransaction().replace(R.id.fl_userList,firebaseAddUserFragment)
            .addToBackStack(null)
            .commit()
        }
    }

    private fun recyclerView() {

        databaseReference = firebaseDatabase.getReference("userData").child(firebaseUser!!.uid)
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                        fireBaseData= ArrayList()
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


