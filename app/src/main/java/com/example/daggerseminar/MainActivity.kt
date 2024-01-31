package com.example.daggerseminar

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private val appUpdater: AppUpdater by lazy { AppUpdater(this) }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.textView).apply {
            text = "Версия приложения: ${BuildConfig.VERSION_NAME}"
        }

        val isLastVersion = BuildConfig.VERSION_CODE >= 3
        findViewById<TextView>(R.id.lastVersion).apply {
            this.visibility = if(isLastVersion) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        findViewById<Button>(R.id.installButton).apply {
            this.visibility = if(isLastVersion) {
                View.GONE
            } else {
                View.VISIBLE
            }
            setOnClickListener {
                it.visibility = View.GONE
                lifecycleScope.launch(Dispatchers.Main) {
                    appUpdater.update()
                }
            }
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (Build.VERSION.SDK_INT >= 33) {
                val hasPermission = ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
                if (hasPermission) {
                    createNotificationChannel()
                } else {
                    val launcher = registerForActivityResult(
                        ActivityResultContracts.RequestPermission()
                    ) { isGranted: Boolean? ->
                        if (isGranted == true) {
                            createNotificationChannel()
                        }
                    }
                    launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                createNotificationChannel()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(App.CHANNEL_ID, "App update channel", NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)
    }


}

fun isNAndAbove(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
}

fun isSAndAbove(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
}


@get:JvmSynthetic
internal val UPDATE_CURRENT_FLAGS = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
    PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
} else {
    PendingIntent.FLAG_UPDATE_CURRENT
}

