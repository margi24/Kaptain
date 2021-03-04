package com.example.kaptain.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "poi_table")
data class PointOfInterest(
        @PrimaryKey
        val id: Long,
        @Embedded
        val mapLocation: MapLocation,
        val name: String,
        val poiType: String,
        @Embedded
        val reviewSummary: ReviewSummary
)

data class MapLocation(
        val latitude: Double,
        val longitude: Double
)

data class ReviewSummary(
        val averageRating: Double,
        val numberOfReviews: Int
)

data class Review(
        val id: Long,
        val reviewerName: String,
        val reviewTitle: String,
        val reviewText: String,
        val rating: Double,
        val dateAdded: String,
        val poiId: Long
)
