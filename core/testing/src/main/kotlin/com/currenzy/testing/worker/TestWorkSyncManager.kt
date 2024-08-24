package com.currenzy.testing.worker

import com.currenzy.data.worker.SyncManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

// A test implementation of SyncManager for testing or development purposes
class TestWorkSyncManager : SyncManager {

    // MutableSharedFlow used to emit and observe the synchronization status
    private val syncingFlow: MutableSharedFlow<Boolean> = MutableSharedFlow(replay = 1)

    // Provides a Flow to observe the synchronization status
    override val isSyncing: Flow<Boolean>
        get() = syncingFlow

    // Emit a new synchronization status value
    // tryEmit will not suspend and returns false if the value could not be emitted
    fun emit(isSyncing: Boolean) {
        syncingFlow.tryEmit(isSyncing)
    }
}