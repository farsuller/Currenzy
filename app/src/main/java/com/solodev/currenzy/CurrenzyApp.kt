package com.solodev.currenzy

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.ExistingWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.currenzy.data.repository.CurrencyRepository
import com.currenzy.data.worker.SyncWorker
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * The application class for the Currenzy app, integrating Dagger-Hilt for dependency injection.
 *
 * This class initializes WorkManager with a unique work request for synchronization and
 * provides a custom WorkerFactory to create workers with injected dependencies.
 */
@HiltAndroidApp
class CurrenzyApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    /**
     * Initializes WorkManager with a unique work request for SyncWorker.
     *
     * This ensures that the SyncWorker is enqueued and managed by WorkManager.
     */
    override fun onCreate() {
        super.onCreate()
        WorkManager.getInstance(this).beginUniqueWork(
            SyncWorker.NAME,
            ExistingWorkPolicy.KEEP,
            SyncWorker.request
        ).enqueue()
    }

    /**
     * Provides the WorkManager configuration with a custom WorkerFactory.
     *
     * @return The configuration for WorkManager, including the custom WorkerFactory.
     */
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
}

/**
 * A custom WorkerFactory for creating workers with injected dependencies using Dagger-Hilt.
 *
 * @param currencyRepository The repository instance to be injected into workers.
 */
class HiltWorkerFactory @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : WorkerFactory() {

    /**
     * Creates a worker with the provided context, class name, and parameters.
     *
     * @param appContext The application context.
     * @param workerClassName The class name of the worker to be created.
     * @param workerParameters Parameters for the worker.
     * @return An instance of the specified worker class, or null if the class is not recognized.
     */
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            SyncWorker::class.java.name ->
                SyncWorker(appContext, workerParameters, currencyRepository)
            else -> null
        }
    }
}