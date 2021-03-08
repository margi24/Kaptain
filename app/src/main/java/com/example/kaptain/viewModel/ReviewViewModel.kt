package com.example.kaptain.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.kaptain.TAG
import com.example.kaptain.data.PoiDatabase
import com.example.kaptain.data.Review
import com.example.kaptain.repository.PoiRepository
import com.example.kaptain.repository.ReviewRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReviewViewModel(application: Application): AndroidViewModel(application) {

    private val reviewRepository: ReviewRepository by lazy {
        ReviewRepository(PoiDatabase.getDatabase(application,viewModelScope).poiDao())
    }

    init {
        Log.d(TAG, "init called")
    }

    private val reviewListLiveData: MutableLiveData<List<Review>> by lazy {
        MutableLiveData<List<Review>>()
    }

    fun getReviewsForPoi(poiId: Long): LiveData<List<Review>> {
        loadReviews(poiId)
        return reviewListLiveData
    }

    private fun loadReviews(poiId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            reviewListLiveData.postValue(reviewRepository.getReviewsForPoi(poiId))
        }
    }

}