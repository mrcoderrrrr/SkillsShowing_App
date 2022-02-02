package com.example.demoapps.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demoapps.R
import com.example.demoapps.databinding.FragmentFileOperationBinding

class FileOperationFragment : Fragment() {
private lateinit var dataBinding:FragmentFileOperationBinding
    private var image: Uri? = null
    private var video: Uri? = null
    val mediaController:MediaController? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MediaController(activity?.applicationContext)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding= DataBindingUtil.inflate(inflater ,R.layout.fragment_file_operation, container, false)
        val view=dataBinding.root
        setClick()
        return view
    }

    private fun setClick() {
        dataBinding.btnImage.setOnClickListener {
            imageFromFile()
        }

        dataBinding.btnVideo.setOnClickListener {
            videoFromFile()
        }

    }

    private fun videoPlay() {
        mediaController?.setAnchorView(dataBinding.vvVideo)
      dataBinding.vvVideo.setMediaController(mediaController)
        dataBinding.vvVideo.setVideoURI(video)
        dataBinding.vvVideo.requestFocus()
        dataBinding.vvVideo.resume()
        dataBinding.vvVideo.pause()
        dataBinding.vvVideo.stopPlayback()
        dataBinding.vvVideo.start()
    }

    private fun videoFromFile() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "video/*"
        startActivityForResult(intent, 200)
    }

    private fun imageFromFile() {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == AppCompatActivity.RESULT_OK) {
            image = data?.data
            dataBinding.ivImage.setImageURI(image)
        }
        if (requestCode == 200 && resultCode == AppCompatActivity.RESULT_OK){
            video = data?.data
            videoPlay()
        }

    }
}
