package com.currenzy.data.di

import com.currenzy.data.repository.CurrencyRepository
import com.currenzy.data.repository.OfflineFirstCurrencyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCurrencyRepository(impl: OfflineFirstCurrencyRepository): CurrencyRepository
}