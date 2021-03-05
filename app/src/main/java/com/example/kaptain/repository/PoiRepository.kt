package com.example.kaptain.repository

import com.example.kaptain.data.PoiDao
import com.example.kaptain.data.PoiData
import com.example.kaptain.data.PointOfInterest
import com.example.kaptain.data.poiList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PoiRepository(private val poiDao: PoiDao) {

    suspend fun getPoiList()= poiDao.getAllPoi()

    suspend fun getPoi(id: Long) = poiDao.getPoi(id)

    suspend fun getPoiDataList() = poiDao.getPoiDataList()

    suspend fun getPoiData(id: Long): PoiData = poiDao.getPoiData(id)
}