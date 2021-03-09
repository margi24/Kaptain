package com.example.kaptain.network

import com.example.kaptain.data.ReviewSummary
import com.example.kaptain.model.MapBoundingBox
import retrofit2.Call

class MockWebservice : Webservice {
    override fun getPoiList(bbBox: MapBoundingBox): Call<PoiListResponse> {
        TODO("Not yet implemented")
    }

    override fun getPoiSummary(id:Long): Call<ReviewSummaryResponse> {
        TODO("Not yet implemented")
    }
}