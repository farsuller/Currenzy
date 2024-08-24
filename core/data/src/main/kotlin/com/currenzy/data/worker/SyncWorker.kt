package com.currenzy.data.worker

import Synchronizer
import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import com.currenzy.data.repository.CurrencyRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
// The SyncWorker class is used for performing background tasks related to synchronization
@HiltWorker
class SyncWorker @AssistedInject constructor(
    // Context provided to the worker (e.g., application context)
    @Assisted private val appContext: Context,
    // Parameters for the worker, such as input data and unique worker ID
    @Assisted workerParams: WorkerParameters,
    // Repository that handles currency data
    private val currencyRepository: CurrencyRepository
) : CoroutineWorker(appContext, workerParams) {

    // Override the doWork function to define the task to be performed
    override suspend fun doWork(): Result {
        return try {
            // Perform the synchronization using the currencyRepository and a new Synchronizer instance
            currencyRepository.syncWith(Synchronizer())
            // Return success if synchronization is completed without exceptions
            Result.success()
        } catch (e: Exception) {
            // Return failure if an exception occurs during synchronization
            Result.failure()
        }
    }

    companion object {
        // Name of the worker, used for identification
        const val NAME = "SyncWorker"

        // Builder for creating a OneTimeWorkRequest to enqueue this worker
        val request = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(WorkerUtil.DefaultConstraints) // Set default constraints for when the worker can run
            .build()
    }
}