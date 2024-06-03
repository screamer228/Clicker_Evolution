package com.example.clickerevolution.data.room.skins

import android.content.Context
import android.provider.ContactsContract.CommonDataKinds
import android.util.Log
import androidx.annotation.RawRes
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.clickerevolution.R
import com.example.clickerevolution.common.Rarity
import com.example.clickerevolution.data.room.skins.entity.SkinEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [SkinEntity::class], version = 3, exportSchema = false)
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
                    .fallbackToDestructiveMigration()
//                    .addMigrations(MIGRATION_2_3)
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
                        populateDatabase(database.skinDao())
                    }
                }
            }

            suspend fun populateDatabase(skinDao: SkinDao) {
                val initialSkinsList = listOf(
                    SkinEntity(
                        1, "Микрочел", R.drawable.img_skin1, R.raw.sound_click, 100,
                        Rarity.COMMON
                    ),
                    SkinEntity(
                        2,
                        " Челикс ",
                        R.drawable.img_skin2,
                        R.raw.sound_cookie_click,
                        300,
                        Rarity.COMMON
                    ),
                    SkinEntity(
                        3,
                        "Горемыка",
                        R.drawable.img_skin3,
                        R.raw.sound_cookie_click,
                        500,
                        Rarity.RARE
                    ),
                    SkinEntity(
                        4,
                        "Милашка",
                        R.drawable.img_skin4,
                        R.raw.sound_cookie_click,
                        1000,
                        Rarity.RARE
                    ),
                    SkinEntity(
                        5,
                        "Оболдуй",
                        R.drawable.img_skin5,
                        R.raw.sound_cookie_click,
                        2000,
                        Rarity.EPIC
                    ),
                    SkinEntity(
                        6,
                        "Жопа)",
                        R.drawable.img_skin_ass,
                        R.raw.sound_cookie_click,
                        5000,
                        Rarity.LEGENDARY
                    )
                )
                skinDao.insertSkins(initialSkinsList)
                Log.d("populateDatabase check", "populateSkinsDatabase()")
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                // Реализация миграции базы данных
                db.execSQL("DROP TABLE skins")
                db.execSQL("CREATE TABLE skins (id INTEGER PRIMARY KEY NOT NULL, title TEXT NOT NULL, imageId INTEGER NOT NULL, soundId INTEGER NOT NULL, price INTEGER NOT NULL, isPurchased INTEGER NOT NULL, isEquipped INTEGER NOT NULL)")
            }
        }
    }
}