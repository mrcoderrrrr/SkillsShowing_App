package com.example.demoapps.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.demoapps.R
import com.example.demoapps.`class`.MyCanvasView
import com.example.demoapps.databinding.FragmentCustomDrawingBinding


class CustomDrawingFragment : Fragment() {
    private lateinit var dataBinding: FragmentCustomDrawingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_custom_drawing, container, false)

        val myCanvasView=MyCanvasView(requireContext())
        myCanvasView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        requireActivity().setContentView(myCanvasView)
        val view = dataBinding.root
        return view
    }
}