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
import com.example.demoapps.adapter.FirebaseAdapter
import com.example.demoapps.adapter.RecyclerViewAdapter
import com.example.demoapps.adapter.RetrofitAdapter
import com.example.demoapps.databinding.FragmentApiBinding
import com.example.demoapps.entity.UserEntity
import com.example.demoapps.model.GraphData
import com.example.demoapps.model.ListData
import com.example.demoapps.model.RetrofitModel
import com.example.demoapps.retrofit.RetrofitApiInterface
import com.example.demoapps.retrofit.RetrofitHelper
import com.github.mikephil.charting.charts.LineChart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ApiFragment : Fragment() {
    private lateinit var dataBinding: FragmentApiBinding
    private var retrofitAdapter: RecyclerView.Adapter<RetrofitAdapter.ViewHolder>? = null
    private lateinit var apiData: ArrayList<RetrofitModel>

    private lateinit var apiListData: ArrayList<ListData>
    private var retrofitModel: RetrofitModel? = null

    private var listData: ListData? = null
    private var graphData: GraphData? = null

    private lateinit var apiGraphData: ArrayList<GraphData>
    private var chart: LineChart? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_api, container, false)
        val view = dataBinding.root
        setClick()
        return view
    }

    private fun setClick() {
        retrofit()
        // graphView()
    }

    private fun retrofit() {
        val retrofitData = RetrofitHelper.getInstance().create(RetrofitApiInterface::class.java)

            CoroutineScope(Dispatchers.Main).launch {
                val result = retrofitData.getApiData()
                if (result != null) {
                    apiData= ArrayList<RetrofitModel>()
                    retrofitModel= RetrofitModel(result.body()?.status.toString() ,
                            result.body()?.message.toString() ,
                            result.body()?.method.toString() ,
                            result.body()!!.data ,
                            result.body()!!.success.toString())
                    apiListData = ArrayList<ListData>()
                    for (data in retrofitModel?.data!!.list_data) {
                        listData = ListData(
                            data.date.toString(), data.difference.toInt(),
                            data.heigh.toString(), data.is_increased
                        )
                        apiListData.add(listData!!)
                    }
                    apiData.add(retrofitModel!!)
                    Log.d(
                        "userData", result.body()?.status.toString() +
                                result.body()?.message.toString() +
                                result.body()?.method.toString() +
                                result.body()!!.data +
                                result.body()!!.success.toString()
                    )

                    activity?.runOnUiThread(Runnable {
                        kotlin.run {
                            dataBinding.rcvApiData.apply {
                                layoutManager=LinearLayoutManager(requireContext())
                                dataBinding.rcvApiData.layoutManager = layoutManager
                                retrofitAdapter = RetrofitAdapter(requireContext(), apiData!!)
                                dataBinding.rcvApiData.adapter =retrofitAdapter
                                (retrofitAdapter as RetrofitAdapter).notifyDataSetChanged()
                            }
                        }
                    })
                }
        }
    }

  /*  private fun graphView() {
        val retrofitData = RetrofitHelper.getInstance().create(RetrofitApiInterface::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val result = retrofitData.getApiData()
            CoroutineScope(Dispatchers.IO).launch {
                if (result != null) {
                  apiData= ArrayList<RetrofitModel>()
                    apiGraphData = ArrayList<GraphData>()
                    chart = LineChart(requireContext())
                    var entry: ArrayList<Entry>? = ArrayList<Entry>()
                    for (graph in retrofitModel!!.data.graph_data) {
                        graphData= GraphData(graph.date,graph.heigh)
                    }
                    apiData.add(retrofitModel!!)
                    if (entry != null) {
                        entry.add(Entry(graphData!!.date.length.toFloat(), graphData!!.heigh.length))
                    }
                    var lineDataSet = LineDataSet(entry, "GraphData")
                     var lineData=LineData(lineDataSet)
                    chart!!.data
                }
            }
        }
    }*/
}

