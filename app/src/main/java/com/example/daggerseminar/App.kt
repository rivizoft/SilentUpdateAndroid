package com.example.daggerseminar

import android.app.Application
import androidx.work.Configuration

/**
 * @author a.s.korchagin
 */


class App : Application(), Configuration.Provider {


    companion object {
        const val CHANNEL_ID = "MyChannel"
    }

    override val workManagerConfiguration: Configuration = Configuration.Builder()
        .setDefaultProcessName("com.example.daggerseminar:updater")
        .setMinimumLoggingLevel(android.util.Log.DEBUG)
        .build()
}

