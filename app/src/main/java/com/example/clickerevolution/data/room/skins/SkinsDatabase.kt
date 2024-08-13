package com.example.clickerevolution.data.room.skins

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.clickerevolution.R
import com.example.clickerevolution.common.Currency
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
                        title = " Микрочел ",
                        imageId = R.drawable.img_skin1,
                        soundId = R.raw.sound_click,
                        priceType = Currency.GOLD,
                        priceValue = 100,
                        rarity = Rarity.COMMON
                    ),
                    SkinEntity(
                        title = " Обжора ",
                        imageId = R.drawable.img_skin2,
                        soundId = R.raw.sound_cookie_click,
                        priceType = Currency.GOLD,
                        priceValue = 500,
                        rarity = Rarity.COMMON
                    ),
                    SkinEntity(
                        title = " Младенец ",
                        imageId = R.drawable.img_skin_baby,
                        soundId = R.raw.sound_cookie_click,
                        priceType = Currency.GOLD,
                        priceValue = 2000,
                        rarity = Rarity.RARE
                    ),
                    SkinEntity(
                        title = " Ситх ",
                        imageId = R.drawable.img_skin_mage,
                        soundId = R.raw.sound_cookie_click,
                        priceType = Currency.GOLD,
                        priceValue = 100,
                        rarity = Rarity.EPIC
                    ),
                    SkinEntity(
                        title = " Жопа)",
                        imageId = R.drawable.img_skin_ass,
                        soundId = R.raw.sound_skin6,
                        priceType = Currency.GOLD,
                        priceValue = 200,
                        rarity = Rarity.EPIC
                    ),
                    SkinEntity(
                        title = " Король ",
                        imageId = R.drawable.img_skin_king,
                        soundId = R.raw.sound_skin7,
                        priceType = Currency.DIAMOND,
                        priceValue = 1,
                        rarity = Rarity.LEGENDARY
                    ),
                    SkinEntity(
                        title = "Костлявый",
                        imageId = R.drawable.img_skin7,
                        soundId = R.raw.sound_skin7,
                        priceType = Currency.DIAMOND,
                        priceValue = 1,
                        rarity = Rarity.LEGENDARY
                    ),
                    SkinEntity(
                        title = " Нежить ",
                        imageId = R.drawable.img_skin8,
                        soundId = R.raw.sound_skin8,
                        priceType = Currency.DIAMOND,
                        priceValue = 200,
                        rarity = Rarity.LEGENDARY
                    )
                )
                skinDao.insertSkins(initialSkinsList)
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