package com.example.kaptain.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kaptain.TAG
import com.example.kaptain.data.PointOfInterest
import com.example.kaptain.repository.PoiRepository
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PoiViewModel : ViewModel() {

    init {
        Log.d(TAG, "init called")
        repeatRequest()
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
            PoiRepository.getPoiList().collect {
                poiListLiveData.postValue(it)
                isLoading.postValue(false)
            }
        }
    }

    private fun loadPoi(id: Long) {
        isLoading.postValue(true)
        viewModelScope.launch(IO) {
            poiLiveData.postValue(PoiRepository.getPoi(id))
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