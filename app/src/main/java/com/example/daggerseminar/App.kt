package com.example.daggerseminar

import android.app.Application
import android.content.Context
import com.example.daggerseminar.di.ApplicationComponent
import com.example.daggerseminar.di.DaggerApplicationComponent

/**
 * @author a.s.korchagin
 */
class App : Application() {

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent.factory()
            .create(this)
    }
}

fun Context.getAppComponent(): ApplicationComponent = (this.applicationContext as App).appComponent
