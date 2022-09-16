package com.example.demoapps.fragment.bottomNavigation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.demoapps.R
import com.example.demoapps.activity.MainActivity
import com.example.demoapps.databinding.FragmentBottomBinding


class BottomFragment : Fragment() {
    private lateinit var dataBinding: FragmentBottomBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding= DataBindingUtil.inflate(inflater ,R.layout.fragment_bottom, container, false)
        val view=dataBinding.root
        // Inflate the layout for this fragment
       setClick()
        return view
    }

    private fun setClick() {
        bottomMenu()
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

    private fun bottomMenu() {
        dataBinding.bnBottomlist.setOnNavigationItemSelectedListener {
            var tempFragment:Fragment? = Fragment()
            when(it.itemId){
                R.id.nav_home ->{
                    tempFragment= BottomHomeFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fl_bottom,tempFragment)
                        .commit()
                }
                R.id.nav_userList ->{
                    tempFragment= BottomUserListFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fl_bottom,tempFragment)
                        .commit()
                }
                R.id.nav_profile ->{
                    tempFragment= BottomProfileFragment()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fl_bottom,tempFragment)
                        .commit()
                }
            }
            true
        }
    }
}