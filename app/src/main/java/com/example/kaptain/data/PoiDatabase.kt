package com.example.kaptain.data

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [PointOfInterest::class, MapLocation::class, ReviewSummary::class, Review::class],version = 3)
abstract class PoiDatabase: RoomDatabase() {

    abstract fun poiDao(): PoiDao

    companion object {
        @Volatile
        private var INSTANCE: PoiDatabase? = null
        private var DATABASE_NAME = "poi-database"

        fun getDatabase(context: Context, scope: CoroutineScope): PoiDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance =  Room.databaseBuilder(
                    context.applicationContext,
                    PoiDatabase::class.java,
                    DATABASE_NAME
                )
                    .addMigrations(MIGRATION_2_3)
                    .addCallback(PoiDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE `point_table` (`id` LONG NOT NULL, `name` TEXT NOT NULL," +
                            "`poiType` TEXT NOT NULL, PRIMARY KEY(`id`))"
                )
                database.execSQL(
                    "CREATE TABLE `map_location_table` (`map_id` LONG NOT NULL,`poiId` LONG NOT NULL,"+
                            "`latitude` DOUBLE NOT NULL, `longitude` DOUBLE NOT NULL," +
                            "PRIMARY KEY(`map_id`))"
                )
                database.execSQL(
                    "CREATE TABLE `review_summary_table` (`review_summary_id` LONG NOT NULL," +
                            "`poiId` LONG NOT NULL, `averageRating` DOUBLE NOT NULL," +
                            "`numberOfReviews` INTEGER NOT NULL, PRIMARY KEY(`review_summary_id`))"
                )
                database.execSQL(
                    "DROP TABLE poi_table"
                )
            }
        }

        private val MIGRATION_2_3 = object: Migration(2,3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE `review_table` (`id` LONG NOT NULL, `reviewerName` TEXT NOT NULL, " +
                            "`reviewTitle` TEXT NOT NULL, `reviewText` TEXT NOT NULL, `rating` DOUBLE NOT NULL," +
                            "`dateAdded` TEXT NOT NULL, `poiId` LONG NOT NULL) "
                )
            }
        }

        class PoiDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.poiDao())
                    }
                }
            }

            private suspend fun populateDatabase(poiDao: PoiDao) {
                poiDao.insertAllPoi(poiList)
                poiDao.insertAllMapLocation(mapLocation)
                poiDao.insertAllReviewSummary(reviewSummaryList)
                poiDao.insertAllReview(reviewList)
            }
        }
    }
}