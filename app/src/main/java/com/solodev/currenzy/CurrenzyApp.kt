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

@HiltAndroidApp
class CurrenzyApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory : HiltWorkerFactory

    override fun onCreate() {
        super.onCreate()
        WorkManager.getInstance(this).beginUniqueWork(
            SyncWorker.NAME,
            ExistingWorkPolicy.KEEP,
            SyncWorker.request
        ).enqueue()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

}

class HiltWorkerFactory @Inject constructor(
    private val currencyRepository: CurrencyRepository
): WorkerFactory(){
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when(workerClassName){
            SyncWorker::class.java.name ->
                SyncWorker(appContext, workerParameters, currencyRepository)
            else -> null

        }
    }

}