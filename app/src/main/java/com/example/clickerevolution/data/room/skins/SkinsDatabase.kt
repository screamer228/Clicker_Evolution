package com.example.clickerevolution.data.room.skins

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.clickerevolution.R
import com.example.clickerevolution.data.room.skins.entity.SkinEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [SkinEntity::class], version = 2, exportSchema = false)
abstract class SkinsDatabase : RoomDatabase() {
    abstract fun skinDao(): SkinDao

    companion object {
        @Volatile
        private var INSTANCE: SkinsDatabase? = null

        fun getDatabase(context: Context): SkinsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SkinsDatabase::class.java,
                    "skins_db"
                )
                    .addCallback(DatabaseCallback())
                    .addMigrations(MIGRATION_1_2)
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
                        populateDatabase(database.skinDao())
                    }
                }
            }

            suspend fun populateDatabase(skinDao: SkinDao) {
                val initialSkinsList = listOf(
                    SkinEntity(1, "Skin 1", R.drawable.img_skin1, R.raw.sound_click, 100),
                    SkinEntity(2, "Skin 2", R.drawable.img_skin2, R.raw.sound_click,300),
                    SkinEntity(3, "Skin 3", R.drawable.img_skin3, R.raw.sound_click,500),
                    SkinEntity(4, "Skin 4", R.drawable.img_skin4, R.raw.sound_click,1000),
                    SkinEntity(5, "Skin 5", R.drawable.img_skin5, R.raw.sound_click,2000),
                )
                skinDao.insertSkins(initialSkinsList)
            }
        }

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Реализация миграции базы данных
                db.execSQL("DROP TABLE skin_table")
                db.execSQL("CREATE TABLE skin_table (id INTEGER PRIMARY KEY NOT NULL, title TEXT NOT NULL, imageId INTEGER NOT NULL, price INTEGER NOT NULL, isPurchased INTEGER NOT NULL, isEquipped INTEGER NOT NULL)")
            }
        }
    }
}