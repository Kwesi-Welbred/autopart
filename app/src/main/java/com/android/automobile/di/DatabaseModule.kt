package com.android.automobile.di

import android.content.Context
import androidx.room.Room
import com.android.automobile.data.dao.CarDao
import com.android.automobile.data.dao.CoverDao
import com.android.automobile.data.dao.MotorDoa
import com.android.automobile.data.repository.ProductRepository
import com.android.automobile.data.source.local.AppDatabase
import com.android.automobile.data.source.local.DatabaseCallback
import com.android.automobile.data.source.local.DatabaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

/*
@Module
@InstallIn(ViewModelComponent::class)
object DatabaseModule {
    @Volatile// Singleton prevents multiple instances of database opening at the same time
    var INSTANCE: AppDatabase? = null

    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        scope: CoroutineScope
    ): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "automobile"
            ).addCallback(DatabaseCallback(context, scope))
                .allowMainThreadQueries()
                .build()
            INSTANCE = instance
            // return instance
            instance
        }
    }


    @Provides
   fun provideProductRepository(
        databaseImpl: DatabaseImpl
    ): ProductRepository {
        return ProductRepository(databaseImpl)
    }

}*/
