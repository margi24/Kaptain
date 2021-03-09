package com.example.kaptain.network

import com.example.kaptain.data.PointOfInterest
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PoiListResponse( @field:Json(name = "pointsOfInterest")  val pointsOfInterest: List<PointOfInterest>)
