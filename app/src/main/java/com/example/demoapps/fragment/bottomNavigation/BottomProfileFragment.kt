package com.example.demoapps.fragment.bottomNavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.demoapps.R
import com.example.demoapps.databinding.FragmentBottomProfileBinding


class BottomProfileFragment : Fragment() {
    private lateinit var dataBinding: FragmentBottomProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {dataBinding= DataBindingUtil.inflate(inflater ,R.layout.fragment_bottom_profile, container, false)
        val view=dataBinding.root
        // Inflate the layout for this fragment
        return view
    }

}