package com.example.kaptain.network

import com.example.kaptain.data.Review
import com.example.kaptain.data.ReviewSummary
import com.example.kaptain.model.MapBoundingBox
import retrofit2.Call
import retrofit2.http.*


class KaptainWebservice : Webservice {

    interface Service {

        @POST("community/api/v1/points-of-interest/bbox")
        fun getPoiList(@Body boundingBox: MapBoundingBox): Call<PoiListResponse>

        //  @GET("community/api/v1/points-of-interest/{poiID}/summary?_={timestamp}")
        @GET("community/api/v1/points-of-interest/{poiID}/summary")
        fun getPoiSummary(@Path("poiID") poiId: Long): Call<ReviewSummaryResponse>


        @GET("community/api/v1/points-of-interest/{poiID}/reviews?_={timestamp}")
        fun getPoiReviews(
            @Path("poiID") poiId: Int,
            @Path("timestamp") timestamp: Long
        ): Call<List<Review>>
    }

    private val service: Service = Api.retrofit.create(Service::class.java)

    override fun getPoiList(bbBox: MapBoundingBox): Call<PoiListResponse> {
        return service.getPoiList(bbBox)
    }

    override fun getPoiSummary(id: Long): Call<ReviewSummaryResponse> {
        return  service.getPoiSummary(id)
    }
}