package com.example.demoapps.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.demoapps.R
import com.example.demoapps.databinding.FragmentFileOperationBinding
import java.io.*

class FileOperationFragment : Fragment() {
private lateinit var dataBinding:FragmentFileOperationBinding
    private var image: Uri? = null
    private var video: Uri? = null
    private var doc: Uri? = null
    private var mediaController:MediaController? = null
    private var fileOutputStream: FileOutputStream? = null
    private var fileInputStream: FileInputStream? = null
    private var inputStreamReader: InputStreamReader? = null
    private var bufferedReader: BufferedReader? = null
    private var stringBuilder: StringBuilder?= null
    private var file: File?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
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
        dataBinding.btnDoc.setOnClickListener {
            docFromFile()
        }
        dataBinding.btnSavefile.setOnClickListener {
            saveDocFile()
        }
        dataBinding.btnViewfile.setOnClickListener {
            viewDocFile()
        }

    }

    private fun viewDocFile() {
        if (dataBinding.etUserFileName.text.toString() != null && dataBinding.etUserFileName.text.toString().trim() != ""){
            fileInputStream = requireActivity().openFileInput(dataBinding.etUserFileName.text.toString())
            inputStreamReader= InputStreamReader(fileInputStream)
            bufferedReader = BufferedReader(inputStreamReader)
            stringBuilder= StringBuilder()
            var text: String? = null
            while ({ text = bufferedReader!!.readLine(); text }() != null) {
                stringBuilder!!.append(text)
            }
            //Displaying data on EditText
            dataBinding.etUserFileData.setText(stringBuilder.toString()).toString()


        }
    }

    private fun saveDocFile() {
        fileOutputStream = requireActivity().openFileOutput(dataBinding.etUserFileName.text.toString(),Context.MODE_PRIVATE)
        fileOutputStream!!.write(dataBinding.etUserFileData.text.toString().toByteArray())
        dataBinding.etUserFileName.text.clear()
        dataBinding.etUserFileData.text.clear()
        Toast.makeText(requireContext(),"Data Save",Toast.LENGTH_LONG).show()
        file= File(fileOutputStream.toString())
        if (file!!.exists()){
            dataBinding.tvDocTxt.setText("Aleready Exists")
        }
        else{
            dataBinding.tvDocTxt.setText("New File Created")
        }
    }

    private fun docFromFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type ="application/pdf"
        startActivityForResult(intent, 300)
    }

    private fun videoPlay() {
        mediaController=MediaController(requireContext())
        mediaController?.setAnchorView(dataBinding.vvVideo)
      dataBinding.vvVideo.setMediaController(mediaController)
        dataBinding.vvVideo.setVideoURI(video)
        dataBinding.vvVideo.requestFocus()
        dataBinding.vvVideo.start()
    }

    private fun videoFromFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "video/*"
        startActivityForResult(intent, 200)
    }

    private fun imageFromFile() {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
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
        if (requestCode == 300 && resultCode == AppCompatActivity.RESULT_OK){
            doc = data?.data
            dataBinding.tvDocTxt.setText(doc.toString())
        }

    }
}
