package com.example.kaptain.repository

import com.example.kaptain.data.poiList

object PoiRepository {

    fun getPoiList() = poiList

    fun getPoi(id: Long) = poiList.find { it.id == id }
}