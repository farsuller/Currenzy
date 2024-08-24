package com.currenzy.data.di

import com.currenzy.data.worker.SyncManager
import com.currenzy.data.worker.WorkSyncManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Dagger-Hilt module for providing dependencies related to synchronization management.
 *
 * This module binds the `WorkSyncManager` implementation to the `SyncManager` interface.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class ManagerModule {

    /**
     * Binds the `WorkSyncManager` implementation to the `SyncManager` interface.
     *
     * This ensures that whenever `SyncManager` is injected, an instance of `WorkSyncManager` will be provided.
     *
     * @param impl The `WorkSyncManager` implementation to bind.
     * @return An instance of `SyncManager` provided by Dagger-Hilt.
     */
    @Binds
    abstract fun bindSyncManager(impl: WorkSyncManager): SyncManager
}