package com.example.demoapps.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.demoapps.R
import com.example.demoapps.activity.MainActivity
import com.example.demoapps.databinding.FragmentFileOperationBinding
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader

class FileOperationFragment : Fragment() {
    private lateinit var dataBinding: FragmentFileOperationBinding
    private var image: Uri? = null
    private var video: Uri? = null
    private var doc: Uri? = null
    private var mediaController: MediaController? = null
    private var fileOutputStream: FileOutputStream? = null
    private var fileInputStream: FileInputStream? = null
    private var inputStreamReader: InputStreamReader? = null
    private var bufferedReader: BufferedReader? = null
    private var stringBuilder: StringBuilder? = null
    private var spinner: Spinner? = null
    var spinnerVal: String? = null
    private var spinnerOption = arrayOf("Image", "Video", "Document", "File")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_file_operation, container, false)
        val view = dataBinding.root
        showElements(name = String())
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

        spinnerOpt(Value = String())
        onBackPressed()
    }

    private fun showElements(name:String) {
        if (name == "Image") {
            dataBinding.ivImage.visibility = View.VISIBLE
            dataBinding.btnImage.visibility = View.VISIBLE
            dataBinding.vvVideo.visibility = View.INVISIBLE
            dataBinding.btnVideo.visibility = View.INVISIBLE
            dataBinding.tvDocTxt.visibility = View.INVISIBLE
            dataBinding.btnDoc.visibility = View.INVISIBLE
            dataBinding.etUserFileData.visibility = View.INVISIBLE
            dataBinding.etUserFileName.visibility = View.INVISIBLE
            dataBinding.btnSavefile.visibility = View.INVISIBLE
            dataBinding.btnViewfile.visibility = View.INVISIBLE
        } else if (name == "Video") {
            dataBinding.ivImage.visibility = View.INVISIBLE
            dataBinding.btnImage.visibility = View.INVISIBLE
            dataBinding.vvVideo.visibility = View.VISIBLE
            dataBinding.btnVideo.visibility = View.VISIBLE
            dataBinding.tvDocTxt.visibility = View.INVISIBLE
            dataBinding.btnDoc.visibility = View.INVISIBLE
            dataBinding.etUserFileData.visibility = View.INVISIBLE
            dataBinding.etUserFileName.visibility = View.INVISIBLE
            dataBinding.btnSavefile.visibility = View.INVISIBLE
            dataBinding.btnViewfile.visibility = View.INVISIBLE
        } else if (name == "Document") {
            dataBinding.ivImage.visibility = View.INVISIBLE
            dataBinding.btnImage.visibility = View.INVISIBLE
            dataBinding.vvVideo.visibility = View.INVISIBLE
            dataBinding.btnVideo.visibility = View.INVISIBLE
            dataBinding.tvDocTxt.visibility = View.VISIBLE
            dataBinding.btnDoc.visibility = View.VISIBLE
            dataBinding.etUserFileData.visibility = View.INVISIBLE
            dataBinding.etUserFileName.visibility = View.INVISIBLE
            dataBinding.btnSavefile.visibility = View.INVISIBLE
            dataBinding.btnViewfile.visibility = View.INVISIBLE
        } else if (name == "File") {
            dataBinding.ivImage.visibility = View.INVISIBLE
            dataBinding.btnImage.visibility = View.INVISIBLE
            dataBinding.vvVideo.visibility = View.INVISIBLE
            dataBinding.btnVideo.visibility = View.INVISIBLE
            dataBinding.tvDocTxt.visibility = View.INVISIBLE
            dataBinding.btnDoc.visibility = View.INVISIBLE
            dataBinding.etUserFileData.visibility = View.VISIBLE
            dataBinding.etUserFileName.visibility = View.VISIBLE
            dataBinding.btnSavefile.visibility = View.VISIBLE
            dataBinding.btnViewfile.visibility = View.VISIBLE
        }
    }

    private fun spinnerOpt(Value:String) {
        var arrayAdapter = ArrayAdapter(requireContext(), R.layout.spinner_option, spinnerOption)
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        dataBinding.sOption.adapter = arrayAdapter

        if (Value == null) {
            val spinnerPosition = arrayAdapter.getPosition(Value)
            dataBinding.sOption.setSelection(spinnerPosition)
        }

        dataBinding.sOption.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                spinnerVal = (spinnerOption[p2].toString());
                showElements(spinnerVal!!);
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun onBackPressed() {
        val backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(backPressedCallback)
    }

    private fun viewDocFile() {
        if (dataBinding.etUserFileName.text.toString() != null && dataBinding.etUserFileName.text.toString()
                .trim() != ""
        ) {
            fileInputStream =
                requireActivity().openFileInput(dataBinding.etUserFileName.text.toString())
            inputStreamReader = InputStreamReader(fileInputStream)
            bufferedReader = BufferedReader(inputStreamReader)
            stringBuilder = StringBuilder()
            var text: String? = null
            while ({ text = bufferedReader!!.readLine(); text }() != null) {
                stringBuilder!!.append(text)
            }
            //Displaying data on EditText
            dataBinding.etUserFileData.setText(stringBuilder.toString()).toString()


        }
    }

    private fun saveDocFile() {
        fileOutputStream = requireActivity().openFileOutput(
            dataBinding.etUserFileName.text.toString(),
            Context.MODE_PRIVATE
        )
        fileOutputStream!!.write(dataBinding.etUserFileData.text.toString().toByteArray())
        dataBinding.etUserFileName.text.clear()
        dataBinding.etUserFileData.text.clear()
        Toast.makeText(requireContext(), "Data Save", Toast.LENGTH_LONG).show()
    }

    private fun docFromFile() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"
        startActivityForResult(intent, 300)
    }

    private fun videoPlay() {
        mediaController = MediaController(requireContext())
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
        if (requestCode == 200 && resultCode == AppCompatActivity.RESULT_OK) {
            video = data?.data
            videoPlay()
        }
        if (requestCode == 300 && resultCode == AppCompatActivity.RESULT_OK) {
            doc = data?.data
            dataBinding.tvDocTxt.setText(doc.toString())
        }

    }
}
