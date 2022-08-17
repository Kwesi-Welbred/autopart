package com.welbtech.autopart.di

import android.content.Context
import androidx.room.Room
import com.android.automobile.data.dao.*
import com.android.automobile.data.repository.GetUserData
import com.android.automobile.data.repository.ProductRepository
import com.android.automobile.data.source.local.ALTER_TABLE_MIGRATION_1_2
import com.android.automobile.data.source.local.AppDatabase
import com.android.automobile.data.source.local.DatabaseCallback
import com.welbtech.autopart.data.dao.CarDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module//binds this to hilt
@InstallIn(SingletonComponent::class)//Install it in app container

object DatabaseModule {
    @Volatile
    var databaseInstance: AppDatabase? = null

    //providing database daos instances to hilt
    @Provides
    @Singleton
    fun providesCategoriesDatabase(productDatabase: AppDatabase): CarDao =
        productDatabase.carDao()

    @Provides
    @Singleton
    fun providesCategoryListDatabase(productDatabase: AppDatabase): MotorDoa =
        productDatabase.motorDao()

    @Provides
    @Singleton
    fun providesCartDatabase(productDatabase: AppDatabase): CartDao = productDatabase.cartDao()

    @Provides
    @Singleton
    fun providesFavoriteDatabase(productDatabase: AppDatabase): FavoritesDao =
        productDatabase.favDao()


    @Provides
    @Singleton
    fun providesCoverDatabase(productDatabase: AppDatabase): CoverDao =
        productDatabase.coverDao()

    @Provides
    @Singleton
    fun providesUserDatabase(productDatabase: AppDatabase): UserDao =
        productDatabase.userDao()


    //providing product repository
    @Provides
    fun providesProductRepository(
        carDao: CarDao,
        cartDao: CartDao,
        favoritesDao: FavoritesDao,
        coverDao: CoverDao,
        motorDoa: MotorDoa
    ): ProductRepository = ProductRepository(
        carDao = carDao,
        coverDao = coverDao,
        motorDao = motorDoa,
        cartDao = cartDao,
        favoritesDao = favoritesDao
    )

    //providing user repository

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): GetUserData = GetUserData(userDao = userDao)

    @Singleton
    @Provides
    fun providesCoroutineScope(): CoroutineScope {
        // Run this code when providing an instance of CoroutineScope
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context, scope: CoroutineScope): AppDatabase {
        return databaseInstance ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context, AppDatabase::class.java, "autoparts"
            ).allowMainThreadQueries()
                .addMigrations(ALTER_TABLE_MIGRATION_1_2)
                .addCallback(DatabaseCallback(context, scope))
                .build()
            databaseInstance = instance
            // return instance
            instance
        }
    }


}
