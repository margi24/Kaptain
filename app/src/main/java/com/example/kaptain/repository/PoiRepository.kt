package com.example.kaptain.repository

import com.example.kaptain.data.PointOfInterest
import com.example.kaptain.data.poiList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object PoiRepository {

    suspend fun getPoiList(): Flow<List<PointOfInterest>> = flow{
        delay(2000)
        emit(poiList)
    }

    suspend fun getPoi(id: Long): PointOfInterest? {
        delay(2000)
        return poiList.find { it.id == id }
    }
}