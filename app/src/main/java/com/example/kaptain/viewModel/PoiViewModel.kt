package com.example.kaptain.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.kaptain.TAG
import com.example.kaptain.data.PoiData
import kotlinx.coroutines.flow.collect
import com.example.kaptain.data.PoiDatabase
import com.example.kaptain.data.PointOfInterest
import com.example.kaptain.repository.PoiRepository
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PoiViewModel(application: Application) : AndroidViewModel(application) {

    private var poiRepository: PoiRepository

    init {
        Log.d(TAG, "init called")
        val poiDao = PoiDatabase.getDatabase(application, viewModelScope).poiDao()
        poiRepository = PoiRepository(poiDao)
       // repeatRequest()
    }

    private val poiDataListLiveData: MutableLiveData<List<PoiData>> by lazy {
        MutableLiveData<List<PoiData>>()
    }

    private val poiListLiveData: MutableLiveData<List<PointOfInterest>> by lazy {
        MutableLiveData<List<PointOfInterest>>()
    }

    private val poiLiveData: MutableLiveData<PointOfInterest> by lazy {
        MutableLiveData<PointOfInterest>()
    }

    private val poiDataLiveData: MutableLiveData<PoiData> by lazy {
        MutableLiveData<PoiData>()
    }

    private val isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun getPoi(id: Long): LiveData<PointOfInterest> {
        loadPoi(id)
        return poiLiveData
    }

    fun getPoiList(): LiveData<List<PointOfInterest>> {
        loadPoiList()
        return poiListLiveData
    }

    fun getPoiData(poiId: Long): LiveData<PoiData> {
        loadPoiData(poiId)
        return poiDataLiveData
    }

    fun getPoiDataList(): LiveData<List<PoiData>> {
        loadPoiDataList()
        return poiDataListLiveData
    }

    private fun loadPoiDataList() {
        isLoading.postValue(true)
        viewModelScope.launch(IO) {
                poiDataListLiveData.postValue(poiRepository.getPoiDataList())
                isLoading.postValue(false)
        }
    }

    private fun loadPoiList() {
        isLoading.postValue(true)
        viewModelScope.launch(IO) {
            poiRepository.getPoiList().collect {
                poiListLiveData.postValue(it)
                isLoading.postValue(false)
            }
        }
    }

    private fun loadPoi(id: Long) {
        isLoading.postValue(true)
        viewModelScope.launch(IO) {
            poiRepository.getPoi(id).collect {
                poiLiveData.postValue(it)
                isLoading.postValue(false)
            }
        }
    }

    private fun loadPoiData(poiId: Long) {
        isLoading.postValue(true)
        viewModelScope.launch(IO) {
            delay(2000)
            poiDataLiveData.postValue(poiRepository.getPoiData(poiId))
            isLoading.postValue(false)
        }
    }

    fun getLoading() = isLoading

    private fun repeatRequest() {
        viewModelScope.launch(IO + CoroutineName("Repeat")) {
            while (true) {
                loadPoiList()
                delay(5000)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared() called")
    }
}