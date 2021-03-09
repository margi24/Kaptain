package com.example.kaptain.repository

import com.example.kaptain.EmptyResultException
import com.example.kaptain.KaptainException
import com.example.kaptain.data.PoiDao
import com.example.kaptain.data.PoiData
import com.example.kaptain.data.PointOfInterest
import com.example.kaptain.data.ReviewSummary
import com.example.kaptain.model.MapBoundingBox
import com.example.kaptain.network.MockWebservice
import com.example.kaptain.network.Webservice
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

class PoiRepository(
    private val poiDao: PoiDao,
    private val webService: Webservice = MockWebservice()
) {

    private val dataIsStale = true

    suspend fun getPoiList()= poiDao.getAllPoi()

    suspend fun deletePoi(id: Long) = poiDao.deletePoi(id)

    suspend fun getPoiDataList() = poiDao.getPoiDataList()

    suspend fun getPoiData(id: Long): PoiData = poiDao.getPoiData(id)

    suspend fun getPoiSummary(id:Long): ReviewSummary {

        val cacheList = poiDao.getPoiData(id).reviewSummary
        var result = cacheList
        if(cacheList == null || dataIsStale) {
            val response = webService.getPoiSummary(id).execute()
            if(response.isSuccessful) {
                val data = response.body()?.reviewSummary
                if(data != null) {
                    result = data
                } else {
                    throw EmptyResultException("empty list")
                }
            }
        }
        return result ?: throw Exception("something went wrong")
    }
}