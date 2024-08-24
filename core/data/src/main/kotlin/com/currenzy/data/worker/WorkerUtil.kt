package com.currenzy.data.worker

import androidx.work.Constraints
import androidx.work.NetworkType

// Utility object for providing common constraints for WorkManager workers
object WorkerUtil {
    // Default constraints used for workers to ensure they run only under specific conditions
    val DefaultConstraints = Constraints.Builder()
        // Requires that the device is connected to a network to run the worker
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()
}