package com.example.demoapps.retrofit

import com.example.demoapps.model.GraphData
import com.example.demoapps.model.ListData
import com.example.demoapps.model.RetrofitModel
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitApiInterface {
    @GET("getUserHeightData")
     suspend fun getApiData() : Response<RetrofitModel>
}