package com.example.daggerseminar

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

/**
 * @author k.shiryaev
 */
class BackgroundWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    private val installConstraints by lazy { InstallConstraints(applicationContext) }

    override suspend fun doWork(): Result {
        var count = 0
        while(count < 5) {
            println("test log updater -- app_is_used:    " + installConstraints.isApplicationActiveUsed())
            println("test log updater -- call_is_active: " + installConstraints.isActivePhoneCall())
            count++
            delay(5000)
        }
        return Result.success()
    }
}
