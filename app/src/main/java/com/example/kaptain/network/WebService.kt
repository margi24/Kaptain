package com.example.kaptain.network

import com.example.kaptain.model.MapBoundingBox
import retrofit2.Call

interface Webservice {
    fun getPoiList(bbBox: MapBoundingBox): Call<PoiListResponse>
    fun getPoiSummary(id:Long): Call<ReviewSummaryResponse>
}