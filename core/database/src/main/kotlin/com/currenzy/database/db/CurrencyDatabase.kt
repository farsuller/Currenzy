package com.currenzy.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.currenzy.database.converter.CurrencyRatesConverter
import com.currenzy.database.dao.CurrencyDao
import com.currenzy.database.model.ExchangeRatesEntity

@Database(
    entities = [ExchangeRatesEntity::class],
    version = 1
)
@TypeConverters(CurrencyRatesConverter::class)
abstract class CurrencyDatabase : RoomDatabase() {

    abstract fun dao(): CurrencyDao

    companion object {
        const val DATABASE_NAME = "currency_db"
    }
}