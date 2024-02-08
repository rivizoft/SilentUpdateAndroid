package com.example.daggerseminar

import android.Manifest
import android.app.ActivityManager
import android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND
import android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
import android.app.ActivityManager.RunningAppProcessInfo.IMPORTANCE_TOP_SLEEPING
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioManager
import android.media.AudioManager.MODE_IN_CALL
import android.media.AudioManager.MODE_IN_COMMUNICATION
import android.telecom.TelecomManager
import androidx.core.app.ActivityCompat

/**
 * @author k.shiryaev
 */
class InstallConstraints(private val context: Context) {

    fun isApplicationActiveUsed(): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        val activeProcesses = activityManager.runningAppProcesses ?: return false

        activeProcesses.forEach { process ->
            if (
                isVisibleForUser(process.importance) ||
                isVisibleWithLockedScreen(process.importance) ||
                isInBackground(process.importance)
            ) {
                process.pkgList.forEach { activeProcess ->
                    if (activeProcess == context.packageName) {
                        return true
                    }
                }
            }
        }

        return false
    }

    fun isActivePhoneCall(): Boolean {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val telecomManager = context.getSystemService(Context.TELECOM_SERVICE) as TelecomManager

        return if (
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_PHONE_STATE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            telecomManager.isInCall
        } else {
            val codeMode = audioManager.mode
            isInPhoneCall(codeMode) || isInVoIPCall(codeMode)
        }
    }

    private fun isVisibleForUser(code: Int): Boolean = code == IMPORTANCE_FOREGROUND

    private fun isVisibleWithLockedScreen(code: Int): Boolean = code == IMPORTANCE_TOP_SLEEPING

    private fun isInBackground(code: Int): Boolean = code == IMPORTANCE_BACKGROUND

    private fun isInPhoneCall(code: Int): Boolean = code == MODE_IN_CALL

    private fun isInVoIPCall(code: Int): Boolean = code == MODE_IN_COMMUNICATION

}
