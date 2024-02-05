package com.example.daggerseminar

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

/**
 * @author k.shiryaev
 */
class BackgroundWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        println("CHECK SERVER")
        return Result.success()
    }
}
