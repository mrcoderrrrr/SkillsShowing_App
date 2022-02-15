package com.example.demoapps.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapps.R
import com.example.demoapps.adapter.RetrofitAdapter
import com.example.demoapps.databinding.FragmentApiBinding
import com.example.demoapps.model.RetrofitModel
import com.example.demoapps.retrofit.RetrofitApiInterface
import com.example.demoapps.retrofit.RetrofitHelper
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class ApiFragment : Fragment() {
    private lateinit var dataBinding: FragmentApiBinding
    private var retrofitAdapter: RecyclerView.Adapter<RetrofitAdapter.ViewHolder>? = null
    private lateinit var apiData: ArrayList<RetrofitModel>
    private var chart: LineChart? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_api, container, false)
        val view = dataBinding.root
        setClick()
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chart = LineChart(requireContext())
    }

    private fun setClick() {
        retrofit()
        //retrofitCallBack()
        graphView()
    }

    private fun retrofit() {
        val retrofitData = RetrofitHelper.getInstance().create(RetrofitApiInterface::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            val result = retrofitData.getApiData()
                apiData = ArrayList()
                val resultData = result.body()
                for (data in resultData!!.data.list_data) {
                    apiData.add(resultData)
                }
                Log.d(
                    "userData", result.body()?.status.toString() +
                            result.body()?.message.toString() +
                            result.body()?.method.toString() +
                            result.body()!!.data +
                            result.body()!!.success.toString()
                )

                activity?.runOnUiThread( {
                    kotlin.run {
                        dataBinding.rcvApiData.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            dataBinding.rcvApiData.layoutManager = layoutManager
                            retrofitAdapter = RetrofitAdapter(requireContext(), apiData)
                            dataBinding.rcvApiData.adapter = retrofitAdapter
                            (retrofitAdapter as RetrofitAdapter).notifyDataSetChanged()
                        }
                    }
                })
            }
    }
    private fun graphView() {
        val retrofitData = RetrofitHelper.getInstance().create(RetrofitApiInterface::class.java)
        val entry= ArrayList<Entry>()
        CoroutineScope(Dispatchers.IO).launch {
            val result = retrofitData.getApiData()
            apiData = ArrayList()
            val resultData = result.body()

            activity?.runOnUiThread(Runnable {
                    kotlin.run {
                        if (resultData != null) {
                            apiData.add(resultData)
                            for (i in resultData.data.graph_data){
                                Log.d("GraphVal",i.month+i.height)
                            }
                            for (data in resultData.data.graph_data) {
                                entry.add(
                                    Entry(
                                        data.month.toFloat(),
                                        data.height.toFloat()
                                    )
                                )
                                Log.d("GraphData", data.toString())
                            }
                                val lineDataSet = LineDataSet(entry, "")
                                val lineData = LineData(lineDataSet)
                                chart!!.data =lineData
                                chart!!.notifyDataSetChanged()
                            }

                        }
            })
        }
    }


}

