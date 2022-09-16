package com.example.demoapps.fragment.bottomNavigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.demoapps.R
import com.example.demoapps.databinding.FragmentBottomHomeBinding

class BottomHomeFragment : Fragment() {
    private lateinit var dataBinding: FragmentBottomHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_bottom_home, container, false)
        val view = dataBinding.root
        // Inflate the layout for this fragment
        return view
    }

}