package com.example.daggerseminar

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import android.util.Log

/**
 * @author a.s.korchagin
 */

class TestUpdateInstallReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val status = intent.getIntExtra(PackageInstaller.EXTRA_STATUS, -1)
        Log.i(TAG, "InstallReceiver4:: received $status")
        when (status) {
            PackageInstaller.STATUS_PENDING_USER_ACTION -> {
                val activityIntent =
                    intent.getParcelableExtra<Intent>(Intent.EXTRA_INTENT)!!

                context.startActivity(activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
            }

            PackageInstaller.STATUS_SUCCESS -> {
                Log.i("test___", "PackageInstaller.STATUS_SUCCESS: isResumed ${intent.getBooleanExtra(EXTRA_IS_APP_RESUMED, false)}")
                val delegate = NotificationDelegate(context)
                delegate.showNotification(delegate.createNotification("Новая версия приложения установлена"))
            }

            else -> {
                val msg = intent.getStringExtra(PackageInstaller.EXTRA_STATUS_MESSAGE)

                Log.e(TAG, "received $status and $msg")
            }
        }
    }



    companion object {

        const val EXTRA_IS_APP_RESUMED = "EXTRA_IS_APP_RESUMED"
    }
}

const val TAG = "test____"
