package com.example.demoapps.fragment.signature

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil

import androidx.fragment.app.Fragment
import com.example.demoapps.R
import com.example.demoapps.fragment.signature.MyCanvasView
import com.example.demoapps.activity.MainActivity
import com.example.demoapps.databinding.FragmentCustomDrawingBinding


class CustomDrawingFragment : Fragment() {
    private lateinit var dataBinding: FragmentCustomDrawingBinding
    private var extraBitmap: Bitmap? = null
    private lateinit var extraCanvas: Canvas
    private lateinit var myCanvasView: MyCanvasView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_custom_drawing, container, false)
        myCanvasView = MyCanvasView(requireContext())
        myCanvasView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        dataBinding.cvSignature.addView(myCanvasView)
        setClick()
        val view = dataBinding.root
        return view
    }

    private fun setClick() {
        dataBinding.btnSavefile.setOnClickListener {
            // myCanvasView.setClick()
            val bitmap = myCanvasView.getBitmap()
            dataBinding.ivSignature.setImageBitmap(bitmap)
        }
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()

    }

}