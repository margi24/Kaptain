package com.example.kaptain.repository

import com.example.kaptain.data.reviewList

object ReviewRepository {

    fun getReviews () = reviewList

    fun getReviewsForPoi (id: Long) = reviewList.filter { it.poiId == id }
}