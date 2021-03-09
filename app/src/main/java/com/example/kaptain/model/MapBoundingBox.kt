package com.example.kaptain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MapBoundingBox(
    @field:Json(name = "north") val north: Double,
    @field:Json(name = "south") val south: Double,
    @field:Json(name = "east") val east: Double,
    @field:Json(name = "west") val west: Double,
    @field:Json(name = "zoomLevel") val zoomLevel: Int = 13
)