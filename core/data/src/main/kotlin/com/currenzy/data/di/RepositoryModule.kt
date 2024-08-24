package com.currenzy.data.di

import com.currenzy.data.repository.CurrencyRepository
import com.currenzy.data.repository.OfflineFirstCurrencyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Dagger-Hilt module for providing repository dependencies.
 *
 * This module binds the `OfflineFirstCurrencyRepository` implementation to the `CurrencyRepository` interface.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Binds the `OfflineFirstCurrencyRepository` implementation to the `CurrencyRepository` interface.
     *
     * This ensures that whenever `CurrencyRepository` is injected, an instance of `OfflineFirstCurrencyRepository` will be provided.
     *
     * @param impl The `OfflineFirstCurrencyRepository` implementation to bind.
     * @return An instance of `CurrencyRepository` provided by Dagger-Hilt.
     */
    @Binds
    abstract fun bindCurrencyRepository(impl: OfflineFirstCurrencyRepository): CurrencyRepository
}