package com.example.kaptain.data

import androidx.room.*

@Entity(tableName = "poi_table")
data class PointOfInterest(
        @PrimaryKey
        val id: Long,
        val name: String?,
        val poiType: String
)
@Entity(tableName = "map_location_table",
        foreignKeys = [ForeignKey(
                entity = PointOfInterest::class,
                parentColumns = ["id"],
                childColumns = ["poiId"],
                onDelete = ForeignKey.CASCADE)])
data class MapLocation(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "map_id")
        val mapId: Long,
        val poiId: Long,
        val latitude: Double,
        val longitude: Double
){
        constructor(poiId: Long,latitude: Double,longitude: Double): this(0, poiId, latitude, longitude)
}
@Entity(tableName = "review_summary_table",
        foreignKeys = [ForeignKey(
                entity = PointOfInterest::class,
                parentColumns = ["id"],
                childColumns = ["poiId"],
                onDelete = ForeignKey.CASCADE)])
data class ReviewSummary(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "review_summary_id")
        val reviewSummaryId: Long?,
        val poiId: Long?,
        val averageRating: Double,
        val numberOfReviews: Int
) {
    constructor(poiId: Long,averageRating: Double,numberOfReviews: Int): this(0, poiId, averageRating, numberOfReviews)
}

data class PoiData(
        @Embedded val poi: PointOfInterest,
        @Relation(
                parentColumn = "id",
                entityColumn = "poiId"
        )
        val mapLocation: MapLocation,
        @Relation(
                parentColumn = "id",
                entityColumn = "poiId"
        )
        val reviewSummary: ReviewSummary?,
        @Relation(
                parentColumn = "id",
                entityColumn = "poiId"
        )
        val reviews: List<Review>?
)

@Entity(tableName = "review_table",
        foreignKeys = [ForeignKey(
                entity = PointOfInterest::class,
                parentColumns = ["id"],
                childColumns = ["poiId"],
                onDelete = ForeignKey.CASCADE)]
)
data class Review(
        @PrimaryKey
        val id: Long,
        val reviewerName: String = "",
        val reviewTitle: String = "",
        val reviewText: String = "",
        val rating: Double = 0.0,
        val dateAdded: String,
        val poiId: Long
)

