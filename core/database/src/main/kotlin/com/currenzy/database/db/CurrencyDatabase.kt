package com.currenzy.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.currenzy.database.converter.CurrencyRatesConverter
import com.currenzy.database.dao.CurrencyDao
import com.currenzy.database.model.ExchangeRatesEntity

// Database class that defines the Room database configuration for the application
@Database(
    entities = [ExchangeRatesEntity::class], // List of entities (tables) managed by this database
    version = 1 // Database version, used for managing schema migrations
)
@TypeConverters(CurrencyRatesConverter::class) // Register TypeConverter for converting custom types
abstract class CurrencyDatabase : RoomDatabase() {

    // Abstract method to get the DAO for interacting with the ExchangeRatesEntity table
    abstract fun dao(): CurrencyDao

    companion object {
        // Name of the database, used when creating or accessing the database
        const val DATABASE_NAME = "currency_db"
    }
}