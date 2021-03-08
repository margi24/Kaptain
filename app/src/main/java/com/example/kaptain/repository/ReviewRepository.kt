package com.example.kaptain.repository

import com.example.kaptain.data.PoiDao
import com.example.kaptain.data.reviewList

class ReviewRepository(private val poiDao: PoiDao) {

    fun getReviews () = reviewList

    suspend fun getReviewsForPoi (id: Long) = poiDao.getPoiWithReviews(id).reviews
}