package com.example.daggerseminar

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PowerManager
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


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
            this.visibility = if (isLastVersion) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

        findViewById<TextView>(R.id.modelNameText).apply {
            text = "Модель телефона: ${Build.BRAND}"
        }

        findViewById<Button>(R.id.installButton).apply {
            setOnClickListener {
                appUpdater.update()
            }
        }

        findViewById<TextView>(R.id.addToBackgroundButton).apply {
            setOnClickListener {
                if (allowInBackgroundMode().not()) {
                    openSettingsBackgroundRestricted()
                } else {
                    openOptimizedStatusSettingsScreen()
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

    override fun onResume() {
        super.onResume()

        val isWorkingInBg = allowInBackgroundMode()

        findViewById<TextView>(R.id.inBackgroundWorkAllowedText).apply {
            visibility = if (isWorkingInBg) View.VISIBLE else View.GONE
        }
        findViewById<TextView>(R.id.inBackgroundWorkRestrictedText).apply {
            visibility = if (isWorkingInBg.not()) View.VISIBLE else View.GONE
        }
        findViewById<TextView>(R.id.addToBackgroundButton).apply {
            text = if (isWorkingInBg) {
                "Убрать из фоновой работы"
            } else {
                "Добавить в фоновую работу"
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            App.CHANNEL_ID,
            "App update channel",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)
    }

    private fun allowInBackgroundMode(): Boolean {
        return runCatching {
            val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
            pm.isIgnoringBatteryOptimizations(packageName)
        }.getOrElse {
            showErrorToast("Ошибка в получении статуса работы в фоне: $it")
            false
        }
    }

    @SuppressLint("BatteryLife")
    private fun openSettingsBackgroundRestricted() {
        runCatching {
            val intent = Intent(
                Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
                Uri.fromParts("package", packageName, null)
            )
            startActivity(intent)
        }.onFailure {
            showErrorToast("Ошибка на добавление в фон: $it")
        }
    }

    private fun openOptimizedStatusSettingsScreen() {
        runCatching {
            val intent = Intent(
                Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS,
            )
            startActivity(intent)
        }.onFailure {
            showErrorToast("Ошибка на редирект отключения из фона: $it")
        }
    }

    private fun showErrorToast(errorText: String) {
        Toast.makeText(
            this,
            errorText,
            Toast.LENGTH_LONG
        ).show()
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

