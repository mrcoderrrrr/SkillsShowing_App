package com.example.demoapps.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.demoapps.R
import com.example.demoapps.`class`.MyCanvasView
import com.example.demoapps.databinding.FragmentCustomDrawingBinding


class CustomDrawingFragment : Fragment(){
    private lateinit var dataBinding: FragmentCustomDrawingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_custom_drawing, container, false)

        val myCanvasView=MyCanvasView(requireContext())
        myCanvasView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        dataBinding.cvSignature.addView(myCanvasView)
        val view = dataBinding.root
        return view
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

}