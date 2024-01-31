package com.example.daggerseminar

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import android.content.pm.PackageManager
import android.os.Build
import android.os.Process
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

/**
 * @author a.s.korchagin
 */
class AppUpdater(
    private val context: Context,
) {

    private val assets = context.assets
    private val installer: PackageInstaller by lazy { context.packageManager.packageInstaller }

    fun update()  {
        val fileName = updateAppFileName()
        val length = assetFileLength()
        assets.open(fileName).use { apkStream ->
            val params = PackageInstaller.SessionParams(PackageInstaller.SessionParams.MODE_FULL_INSTALL)
                .apply {
                    if (isNAndAbove()) {
                        setOriginatingUid(Process.myUid())
                    }
                    if (isSAndAbove()) {
                        setRequireUserAction(PackageInstaller.SessionParams.USER_ACTION_NOT_REQUIRED)
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        setInstallReason(PackageManager.INSTALL_REASON_USER)
                    }
                    setAppPackageName(context.applicationContext.packageName)
                    setSize(length)

                }

            val sessionId = installer.createSession(params)
            val session = installer.openSession(sessionId)


            session.openWrite(updateAppFileName(), 0, length).use { sessionStream ->
                apkStream.copyTo(sessionStream)
                session.fsync(sessionStream)
            }

            val intent = Intent(context, TestUpdateInstallReceiver::class.java)
                .putExtra(TestUpdateInstallReceiver.EXTRA_IS_APP_RESUMED, true)
            val pi = PendingIntent.getBroadcast(
                context,
                100,
                intent,
                UPDATE_CURRENT_FLAGS
            )

            session.commit(pi.intentSender)
            session.close()
        }
    }

    // просто первый попавшийся реально работающий способ узнать размер файла из assets
    // для тестовой приложухи точно сойдет
    private fun assetFileLength(): Long {
        val filename = "sample.apk"
        val `in`: InputStream = assets.open(updateAppFileName())
        val outFile: File = File(context.cacheDir, filename)
        val out: OutputStream = FileOutputStream(outFile)

        try {
            var len: Int
            val buff = ByteArray(1024)
            while (`in`.read(buff).also { len = it } > 0) {
                out.write(buff, 0, len)
            }
        } finally {
            // close in & out
        }
        return outFile.length()
    }

    private fun updateAppFileName(): String {
        return if (BuildConfig.VERSION_CODE < 2) {
            "app-debug-2.apk"
        } else {
            "app-debug-3.apk"
        }
    }
}
