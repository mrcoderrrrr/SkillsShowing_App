package com.example.demoapps.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapps.R
import com.example.demoapps.entity.UserEntity

class RecyclerViewAdapter(private val context:Context, val UserEntity: ArrayList<UserEntity>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private var userData= ArrayList<UserEntity>()
    init{
        this.userData=userData
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.ViewHolder {
        val v=LayoutInflater.from(parent.context).inflate(R.layout.activity_userlist_cardview,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerViewAdapter.ViewHolder, position: Int) {
        holder.username.setText(userData.get(position).fname+" "+userData.get(position).lname)
        holder.gender.setText(userData.get(position).gender)
    }

    override fun getItemCount(): Int {
return userData.size
    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        lateinit var Profile:ImageView
        lateinit var username:TextView
        lateinit var gender:TextView

        init {
            Profile=itemView.findViewById(R.id.ci_profile)
            username=itemView.findViewById(R.id.tv_username)
            gender=itemView.findViewById(R.id.tv_gender)
        }
    }
}

