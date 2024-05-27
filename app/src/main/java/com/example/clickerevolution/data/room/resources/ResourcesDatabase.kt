package com.example.clickerevolution.data.room.resources

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.clickerevolution.R
import com.example.clickerevolution.data.room.resources.entity.ResourcesEntity
import com.example.clickerevolution.data.room.skins.entity.SkinEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [ResourcesEntity::class], version = 1, exportSchema = false)
abstract class ResourcesDatabase : RoomDatabase() {
    abstract fun resourcesDao(): ResourcesDao

    companion object {
        @Volatile
        private var INSTANCE: ResourcesDatabase? = null

        fun getDatabase(context: Context): ResourcesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ResourcesDatabase::class.java,
                    "resources_db"
                )
                    .fallbackToDestructiveMigration()
//                    .addMigrations(MIGRATION_1_2)
                    .addCallback(DatabaseCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    CoroutineScope(Dispatchers.IO).launch {
                        populateDatabase(database.resourcesDao())
                    }
                }
            }

            suspend fun populateDatabase(skinDao: ResourcesDao) {
                val initialResources = ResourcesEntity(
                    goldClickTickValue = 1,
                    goldTickPerSecValue = 0
                )
                skinDao.insertResources(initialResources)
            }
        }
    }
}