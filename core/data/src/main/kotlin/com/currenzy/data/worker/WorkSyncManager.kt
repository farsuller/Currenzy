package com.currenzy.data.worker

import android.content.Context
import androidx.work.WorkInfo
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
// Interface defining a contract for synchronization management
interface SyncManager {
    // Flow that emits the synchronization status (whether it is currently syncing or not)
    val isSyncing: Flow<Boolean>
}

// Implementation of SyncManager using WorkManager to manage synchronization tasks
class WorkSyncManager @Inject constructor(
    // Application context provided for WorkManager operations
    @ApplicationContext private val context: Context,
) : SyncManager {

    // Override the isSyncing property to provide the synchronization status
    override val isSyncing: Flow<Boolean>
        get() = WorkManager
            // Get the WorkManager instance for the provided context
            .getInstance(context)
            // Retrieve work info for a unique work name defined by SyncWorker.NAME
            .getWorkInfosForUniqueWorkFlow(SyncWorker.NAME)
            // Map the list of WorkInfo to check if any work is currently running
            .map { it.anyRunning() }

}

// Extension function to determine if any of the WorkInfo objects are in the RUNNING state
private fun List<WorkInfo>.anyRunning() = any { it.state == WorkInfo.State.RUNNING }