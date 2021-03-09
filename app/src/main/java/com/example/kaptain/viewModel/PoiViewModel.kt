package com.example.kaptain.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.kaptain.EmptyResultException
import com.example.kaptain.KaptainException
import com.example.kaptain.TAG
import com.example.kaptain.data.*
import kotlinx.coroutines.flow.collect
import com.example.kaptain.network.KaptainWebservice
import com.example.kaptain.repository.PoiRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class PoiViewModel(application: Application) : AndroidViewModel(application) {

    private val poiRepository: PoiRepository by lazy {
        PoiRepository(PoiDatabase.getDatabase(application,viewModelScope).poiDao(), KaptainWebservice())
    }

    init {
        Log.d(TAG, "init called")
    }

    private val poiDataListLiveData: MutableLiveData<List<PoiData>> by lazy {
        MutableLiveData<List<PoiData>>()
    }

    private val poiListLiveData: MutableLiveData<List<PointOfInterest>> by lazy {
        MutableLiveData<List<PointOfInterest>>()
    }

    private val poiDataLiveData: MutableLiveData<PoiData> by lazy {
        MutableLiveData<PoiData>()
    }

    private val poiReviewSummary: MutableLiveData<Resource<ReviewSummary>> by lazy {
        MutableLiveData<Resource<ReviewSummary>>()
    }

    private val isLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
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


    private fun loadPoiData(poiId: Long) {
        isLoading.postValue(true)
        viewModelScope.launch(IO) {
            delay(2000)
            poiDataLiveData.postValue(poiRepository.getPoiData(poiId))
            isLoading.postValue(false)
        }
    }

    fun getPoiSummary(id:Long): LiveData<Resource<ReviewSummary>> {
        viewModelScope.launch(IO) {
            try {
                withContext(Dispatchers.IO) {
                    poiReviewSummary.postValue(Resource.Loading())
                    val poiReview = poiRepository.getPoiSummary(id)
                    val success = Resource.Success<ReviewSummary>(poiReview)
                    poiReviewSummary.postValue(success)
                }
            } catch (e: EmptyResultException) {
                val message = e.message ?: ""
                poiReviewSummary.postValue(Resource.Error(message, type = EmptyResultException(message)))
            }
            catch (e: Exception) {
                val message = e.message ?: ""
                Log.d(TAG, "Exception $message")
                        poiReviewSummary.postValue(Resource.Error(message))

                }
            }
        return poiReviewSummary
    }

    fun deletePoi(poiId:Long) {
        viewModelScope.launch(IO) {
            poiRepository.deletePoi(poiId)
            loadPoiDataList()
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