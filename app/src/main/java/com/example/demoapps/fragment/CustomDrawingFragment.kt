package com.example.demoapps.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.demoapps.R
import com.example.demoapps.`class`.MyCanvasView
import com.example.demoapps.databinding.FragmentCustomDrawingBinding
import java.io.*


class CustomDrawingFragment : Fragment(){
    private lateinit var dataBinding: FragmentCustomDrawingBinding
    private  var extraBitmap: Bitmap? =null
    private lateinit var extraCanvas:Canvas
    private lateinit var myCanvasView: MyCanvasView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_custom_drawing, container, false)

        myCanvasView=MyCanvasView(requireContext())
        myCanvasView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        dataBinding.cvSignature.addView(myCanvasView)
setClick()
        val view = dataBinding.root
        return view
    }

    private fun setClick() {
        dataBinding.btnSavefile.setOnClickListener {
           // myCanvasView.setClick()
            val bitmap=myCanvasView.getBitmap()
            dataBinding.ivSignature.setImageBitmap(bitmap)
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()

    }

}