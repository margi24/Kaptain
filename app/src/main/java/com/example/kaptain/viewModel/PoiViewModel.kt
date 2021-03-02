package com.example.kaptain.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kaptain.TAG
import com.example.kaptain.data.PointOfInterest
import com.example.kaptain.repository.PoiRepository

class PoiViewModel : ViewModel() {

    init {
        Log.d(TAG, "init called")
    }

    private val poiListLiveData: MutableLiveData<List<PointOfInterest>> by lazy {
        MutableLiveData<List<PointOfInterest>>()
    }

    private val poiLiveData: MutableLiveData<PointOfInterest> by lazy {
        MutableLiveData<PointOfInterest>()
    }

    fun getPoi(id: Long): LiveData<PointOfInterest> {
        loadPoi(id)
        return poiLiveData
    }

    fun getPoiList(): LiveData<List<PointOfInterest>> {
        loadPoiList()
        return poiListLiveData
    }

    private fun loadPoiList() {
        poiListLiveData.postValue(PoiRepository.getPoiList())
    }

    private fun loadPoi(id: Long) {
        poiLiveData.postValue(PoiRepository.getPoi(id))
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared() called")
    }
}