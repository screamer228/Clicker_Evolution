package com.example.clickerevolution.data.room.stats

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.clickerevolution.data.room.stats.entity.StatsEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [StatsEntity::class], version = 1, exportSchema = false)
abstract class StatsDatabase : RoomDatabase() {
    abstract fun resourcesDao(): StatsDao

    companion object {
        @Volatile
        private var INSTANCE: StatsDatabase? = null

        fun getDatabase(context: Context): StatsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StatsDatabase::class.java,
                    "stats_db"
                )
                    .fallbackToDestructiveMigration()
//                    .addMigrations(MIGRATION_1_2)
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateDatabase(database.resourcesDao())
                    }
                }
            }

            suspend fun populateDatabase(skinDao: StatsDao) {
                val initialResources = StatsEntity(
                    goldClickTickValue = 1,
                    goldTickPerSecValue = 1,
                    diamondProgressBar = 0
                )
                skinDao.insertResources(initialResources)
            }
        }
    }
}