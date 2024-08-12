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

class OfflineFirstCurrencyRepository @Inject constructor(
    database: CurrencyDatabase,
    private val currencyService: CurrencyService
) : CurrencyRepository {

    val dao = database.dao()

    override fun getExchangeRate(): Flow<ExchangeRates> {
        return dao.getExchangeRates().map { it.asExternalModel() }
    }

    override suspend fun syncWith(synchronizer: Synchronizer) {
        synchronizer.start(
            fetchRemoteData = { currencyService.getExchangeRates() },
            deleteLocalData = { dao.clearExchangeRates() },
            updateLocalData = { dao.upsertExchangeRates(it.toEntity("PHP")) }
        )
    }
}