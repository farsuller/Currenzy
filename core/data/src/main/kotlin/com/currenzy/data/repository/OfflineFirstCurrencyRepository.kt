package com.currenzy.data.repository

import Synchronizer
import com.currenzy.data.mapper.toEntity
import com.currenzy.database.db.CurrencyDatabase
import com.currenzy.database.model.asExternalModel
import com.currenzy.model.currency_converter.ExchangeRates
import com.currenzy.network.service.CurrencyService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Repository implementation that follows an "offline-first" approach.
 *
 * @param database The database instance providing access to the local data.
 * @param currencyService The service used to fetch remote currency data.
 */
class OfflineFirstCurrencyRepository @Inject constructor(
    database: CurrencyDatabase,
    private val currencyService: CurrencyService
) : CurrencyRepository {

    // Data Access Object (DAO) for interacting with the database
    private val dao = database.dao()

    /**
     * Returns a Flow of ExchangeRates from the local database.
     *
     * The data is transformed from the local database entity to the external model.
     *
     * @return A Flow emitting the exchange rates.
     */
    override fun getExchangeRate(): Flow<ExchangeRates> {
        // Fetch exchange rates from the local database and map them to the external model
        return dao.getExchangeRates().map { it.asExternalModel() }
    }

    /**
     * Synchronizes the local database with the remote data source.
     *
     * This involves fetching the latest exchange rates from the remote service,
     * clearing the existing local data, and updating the local data with the new rates.
     *
     * @param synchronizer The synchronizer to handle the data synchronization process.
     */
    override suspend fun syncWith(synchronizer: Synchronizer) {
        synchronizer.start(
            // Fetch remote data from the currency service
            fetchRemoteData = { currencyService.getExchangeRates() },

            // Clear all local exchange rate entries in the database
            deleteLocalData = { dao.clearExchangeRates() },

            // Insert new exchange rates into the local database, converted to the appropriate entity format
            updateLocalData = { dao.upsertExchangeRates(it.toEntity("PHP")) }
        )
    }
}