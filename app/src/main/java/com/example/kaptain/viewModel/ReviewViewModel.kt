package com.example.kaptain.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kaptain.TAG
import com.example.kaptain.data.Review
import com.example.kaptain.repository.ReviewRepository

class ReviewViewModel: ViewModel() {

    init {
        Log.d(TAG, "init called")
    }

    private val reviewListLiveData: MutableLiveData<List<Review>> by lazy {
        MutableLiveData<List<Review>>()
    }

    fun getReviews(): LiveData<List<Review>> {
        reviewListLiveData.postValue(ReviewRepository.getReviews())
        return reviewListLiveData
    }

    fun getReviewsForPoi(poiId: Long): LiveData<List<Review>> {
        reviewListLiveData.postValue(ReviewRepository.getReviewsForPoi(poiId))
        return reviewListLiveData
    }
}