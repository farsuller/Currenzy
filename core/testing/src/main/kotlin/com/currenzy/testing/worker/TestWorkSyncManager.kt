package com.currenzy.testing.worker

import com.currenzy.data.worker.SyncManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TestWorkSyncManager : SyncManager {

    private val syncingFlow : MutableSharedFlow<Boolean> = MutableSharedFlow(replay = 1)
    override val isSyncing: Flow<Boolean>
        get() = syncingFlow

    fun emit(isSyncing : Boolean){
        syncingFlow.tryEmit(isSyncing)
    }
}