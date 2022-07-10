package com.android.automobile.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.automobile.data.dao.*
import com.android.automobile.model.*
import kotlinx.coroutines.CoroutineScope


@Database(
    entities = [
        CarAccessories::class,
        MotorAccessories::class,
        CoverPage::class,
        Cart::class,
        Favorites::class],
    version = 2,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun carDao(): CarDao
    abstract fun motorDao(): MotorDoa
    abstract fun coverDao(): CoverDao
    abstract fun favDao():FavoritesDao
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile// Singleton prevents multiple instances of database opening at the same time
        var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "autoparts"
                ).addMigrations(ALTER_TABLE_MIGRATION_1_2).addCallback(DatabaseCallback(context, scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}