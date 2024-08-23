package com.currenzy.data.di

import com.currenzy.data.worker.SyncManager
import com.currenzy.data.worker.WorkSyncManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ManagerModule {

    @Binds
    abstract fun bindSyncManager(impl : WorkSyncManager) : SyncManager
}