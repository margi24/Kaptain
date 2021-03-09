package com.example.kaptain.network

import com.example.kaptain.data.ReviewSummary
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewSummaryResponse( @field:Json(name = "reviewSummary")  val reviewSummary: ReviewSummary?)
