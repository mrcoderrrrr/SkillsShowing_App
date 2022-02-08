package com.example.demoapps.adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapps.R
import com.example.demoapps.entity.UserEntity
import com.example.demoapps.fragment.FirebaseItemListFragment
import com.example.demoapps.fragment.FirebaseUserFragment
import com.example.demoapps.fragment.UserItemListFragment
import com.example.demoapps.model.FireBaseModel
import com.squareup.picasso.Picasso

class FirebaseAdapter(context:Context, private val fireBaseData: ArrayList<FireBaseModel>) : RecyclerView.Adapter<FirebaseAdapter.ViewHolder>() {
    private val context:Context
    init {
        this.context=context
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirebaseAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.activity_userlist_cardview,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: FirebaseAdapter.ViewHolder, position: Int) {
        holder.username.setText(fireBaseData.get(position).fname+" "+fireBaseData.get(position).lname)
        holder.gender.setText(fireBaseData.get(position).gender)
        Picasso.get()
            .load(fireBaseData.get(position).imagepath)
            .centerCrop()
            .resize(70,70)
            .into(holder.Profile)
        holder.itemView.setOnClickListener {
            val firebaseItemListFragment= FirebaseItemListFragment()
            val activity=it.context as AppCompatActivity
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fl_userList,firebaseItemListFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int {
        return fireBaseData.size
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var Profile: ImageView
        var username: TextView
        var gender: TextView
        init {
            Profile=itemView.findViewById(R.id.ci_profile)
            username=itemView.findViewById(R.id.tv_username)
            gender=itemView.findViewById(R.id.tv_gender)
        }
    }
}