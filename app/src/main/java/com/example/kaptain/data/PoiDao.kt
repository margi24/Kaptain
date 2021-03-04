package com.example.kaptain.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PoiDao {

    @Insert
    suspend fun insertPoi(poi: PointOfInterest)

    @Insert
    suspend fun insertAllPoi(poiList: List<PointOfInterest>)

    @Delete
    suspend fun deletePoi(poi: PointOfInterest)

    @Update
    suspend fun updatePoi(poi: PointOfInterest)

    @Query("SELECT * from poi_table")
    fun getAllPoi(): Flow<List<PointOfInterest>>

    @Query("SELECT * from poi_table WHERE id=:id")
    fun getPoi(id: Long): Flow<PointOfInterest>

}