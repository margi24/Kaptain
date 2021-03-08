package com.example.kaptain.repository

import com.example.kaptain.data.PoiDao
import com.example.kaptain.data.PoiData
import com.example.kaptain.data.PointOfInterest
import kotlinx.coroutines.flow.Flow

class PoiRepository(private val poiDao: PoiDao) {

    suspend fun getPoiList()= poiDao.getAllPoi()

    suspend fun getPoi(id: Long) = poiDao.getPoi(id)

    suspend fun deletePoi(id: Long) = poiDao.deletePoi(id)

    suspend fun getPoiDataList() = poiDao.getPoiDataList()

    suspend fun getPoiData(id: Long): PoiData = poiDao.getPoiData(id)
}