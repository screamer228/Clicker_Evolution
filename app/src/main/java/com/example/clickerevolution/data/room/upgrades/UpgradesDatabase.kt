package com.example.clickerevolution.data.room.upgrades

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.clickerevolution.R
import com.example.clickerevolution.common.Currency
import com.example.clickerevolution.common.UpgradeType
import com.example.clickerevolution.data.room.upgrades.entity.UpgradeEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [UpgradeEntity::class], version = 1, exportSchema = false)
abstract class UpgradesDatabase : RoomDatabase() {
    abstract fun upgradeDao(): UpgradeDao

    companion object {
        @Volatile
        private var INSTANCE: UpgradesDatabase? = null

        fun getDatabase(context: Context): UpgradesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UpgradesDatabase::class.java,
                    "upgrades_db"
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
                        populateDatabase(database.upgradeDao())
                    }
                }
            }

            suspend fun populateDatabase(upgradeDao: UpgradeDao) {
                val initialUpgradesList = listOf(
                    UpgradeEntity(
                        title = "Грязь",
                        power = 1,
                        priceType = Currency.GOLD,
                        priceValue = 500,
                        type = UpgradeType.CLICK_TICK
                    ),
                    UpgradeEntity(
                        title = "Солома",
                        power = 3,
                        priceType = Currency.GOLD,
                        priceValue = 1000,
                        type = UpgradeType.CLICK_TICK
                    ),
                    UpgradeEntity(
                        title = "Мусор",
                        power = 5,
                        priceType = Currency.GOLD,
                        priceValue = 3000,
                        type = UpgradeType.CLICK_TICK
                    ),
                    UpgradeEntity(
                        title = "Пердёж",
                        power = 10,
                        priceType = Currency.GOLD,
                        priceValue = 6000,
                        type = UpgradeType.CLICK_TICK
                    ),
                    UpgradeEntity(
                        title = "Рыготня",
                        power = 25,
                        priceType = Currency.GOLD,
                        priceValue = 12000,
                        type = UpgradeType.CLICK_TICK
                    ),
                    UpgradeEntity(
                        title = "Грязь",
                        power = 1,
                        priceType = Currency.GOLD,
                        priceValue = 500,
                        type = UpgradeType.TICK_PER_SEC
                    ),
                    UpgradeEntity(
                        title = "Солома",
                        power = 3,
                        priceType = Currency.GOLD,
                        priceValue = 1000,
                        type = UpgradeType.TICK_PER_SEC
                    ),
                    UpgradeEntity(
                        title = "Мусор",
                        power = 5,
                        priceType = Currency.GOLD,
                        priceValue = 3000,
                        type = UpgradeType.TICK_PER_SEC
                    ),
                    UpgradeEntity(
                        title = "Пердёж",
                        power = 10,
                        priceType = Currency.GOLD,
                        priceValue = 6000,
                        type = UpgradeType.TICK_PER_SEC
                    ),
                    UpgradeEntity(
                        title = "Рыготня",
                        power = 25,
                        priceType = Currency.GOLD,
                        priceValue = 12000,
                        type = UpgradeType.TICK_PER_SEC
                    ),
                    UpgradeEntity(
                        title = "Сон",
                        description = "Оффлайн сбор становится эффективнее",
                        imageId = R.drawable.img_upgrade_special1_2,
                        power = 5,
                        level = 1,
                        priceType = Currency.DIAMOND,
                        priceValue = 1,
                        type = UpgradeType.SPECIAL
                    ),
                    UpgradeEntity(
                        title = " Жадность ",
                        description = "Вы получаете больше алмазов за заполнение планки",
                        imageId = R.drawable.img_upgrade_special2,
                        power = 1,
                        level = 1,
                        priceType = Currency.DIAMOND,
                        priceValue = 1,
                        type = UpgradeType.SPECIAL
                    )
                )
                upgradeDao.insertUpgrades(initialUpgradesList)
            }
        }
    }
}