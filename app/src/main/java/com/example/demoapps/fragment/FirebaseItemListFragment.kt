package com.example.demoapps.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.demoapps.R
import com.example.demoapps.database.UserDatabase
import com.example.demoapps.databinding.FragmentFirebaseItemListBinding
import com.example.demoapps.model.FireBaseModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso


class FirebaseItemListFragment : Fragment() {
    private lateinit var dataBinding: FragmentFirebaseItemListBinding
    private var firebaseDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var databaseReference: DatabaseReference? = null
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var firebaseUser: FirebaseUser? = firebaseAuth.currentUser
    private  var fireBaseModel: FireBaseModel? =null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_firebase_item_list,
            container,
            false
        )
        val view = dataBinding.root
        setClick()
        return view
    }

    private fun setClick() {
        viewFirebaseData()
        dataBinding.btnUpdate.setOnClickListener {
            updateData(it)
        }
    }

    private fun updateData(view: View) {
        val firebaseAddUserFragment=FirebaseAddUserFragment()
        val fragmentManager=view.context as AppCompatActivity
        fragmentManager.supportFragmentManager.beginTransaction().replace(R.id.fl_userList,firebaseAddUserFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun viewFirebaseData() {
        databaseReference = firebaseDatabase.getReference("UserData").child(firebaseUser!!.uid)
        databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Picasso.get()
                    .load(snapshot.child("imagepath").value as String)
                    .resize(200, 200)
                    .centerCrop()
                    .into(dataBinding.cvProfile)
                dataBinding.tvName.setText(snapshot.child("fname").value as String+" "+snapshot.child("lname").value as String)
                dataBinding.tvGender.setText(snapshot.child("gender").value as String)
                dataBinding.tvDob.setText(snapshot.child("dob").value as String)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}