package com.currenzy.database.di

import android.app.Application
import androidx.room.Room
import com.currenzy.database.db.CurrencyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Dagger module that provides the Room database instance for dependency injection
@Module
@InstallIn(SingletonComponent::class) // Specifies that the provided dependencies should live as long as the application
object DatabaseModule {

    // Provides a singleton instance of CurrencyDatabase
    @Provides
    @Singleton
    fun provideCurrencyDatabase(application: Application): CurrencyDatabase {
        return Room.databaseBuilder(
            application, // Application context needed to build the database
            CurrencyDatabase::class.java, // The class of the database
            CurrencyDatabase.DATABASE_NAME // The name of the database
        )
            // If there's a schema version mismatch, the existing database is destroyed and recreated
            .fallbackToDestructiveMigration()
            .build() // Builds the database instance
    }
}