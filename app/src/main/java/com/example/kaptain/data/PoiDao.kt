package com.example.kaptain.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PoiDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoi(poi: PointOfInterest)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMapLocation(mapLocation: MapLocation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReviewSummary(reviewSummary: ReviewSummary)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllReview(review: List<Review>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPoi(poiList: List<PointOfInterest>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMapLocation(mapList: List<MapLocation>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllReviewSummary(reviewSummaryList: List<ReviewSummary>)

    @Query("DELETE FROM poi_table WHERE id=:id")
    suspend fun deletePoi(id: Long)

    @Update
    suspend fun updatePoi(poi: PointOfInterest)

    @Query("SELECT * from poi_table")
    fun getAllPoi(): Flow<List<PointOfInterest>>

    @Query("SELECT * from poi_table WHERE id=:id")
    fun getPoi(id: Long): Flow<PointOfInterest>

    @Transaction
    @Query("SELECT * FROM poi_table")
    suspend fun getPoiDataList(): List<PoiData>

    @Transaction
    @Query("SELECT * FROM poi_table WHERE id=:id")
    suspend fun getPoiData(id: Long): PoiData

    @Transaction
    @Query("SELECT * FROM poi_table WHERE id=:id")
    suspend fun getPoiWithReviews(id:Long): PoiData
}