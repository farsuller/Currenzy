package com.currenzy.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.currenzy.database.model.ExchangeRatesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    // Upserts (inserts or updates) an ExchangeRatesEntity into the database
    @Upsert
    suspend fun upsertExchangeRates(currencies: ExchangeRatesEntity)

    // Deletes all entries from the ExchangeRatesEntity table
    @Query("DELETE FROM ExchangeRatesEntity")
    suspend fun clearExchangeRates()

    // Retrieves all ExchangeRatesEntity entries from the database as a Flow
    @Query("SELECT * FROM ExchangeRatesEntity")
    fun getExchangeRates(): Flow<ExchangeRatesEntity>
}