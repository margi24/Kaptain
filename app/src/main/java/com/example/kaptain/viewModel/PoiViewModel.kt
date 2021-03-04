package com.example.kaptain.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.kaptain.TAG
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

    private val poiListLiveData: MutableLiveData<List<PointOfInterest>> by lazy {
        MutableLiveData<List<PointOfInterest>>()
    }

    private val poiLiveData: MutableLiveData<PointOfInterest> by lazy {
        MutableLiveData<PointOfInterest>()
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