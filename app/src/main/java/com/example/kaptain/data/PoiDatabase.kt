package com.example.kaptain.data

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [PointOfInterest::class],version = 1)
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
                    .addCallback(PoiDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
        class PoiDatabaseCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                       populateDatabase(database.poiDao())
                    }
                }
            }

            private suspend fun populateDatabase(poiDao: PoiDao) {
                poiDao.insertAllPoi(poiList)
            }
        }
    }
}