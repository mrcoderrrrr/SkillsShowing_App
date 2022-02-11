package com.example.demoapps.model

import retrofit2.Response

data class RetrofitModel(
    val status: String,
    val message: String,
    val method: String,
    val data: Data,
    val success: String
)

data class Data(
    val graph_data: List<GraphData>,
    val list_data: List<ListData>
)

data class ListData(
    val date: String,
    val difference: Int,
    val heigh: String,
    val is_increased: Boolean
)

data class GraphData(
    val date: String,
    val heigh: String
)

//data class list_data(
//    val date: String,
//    val heigh: String
//)