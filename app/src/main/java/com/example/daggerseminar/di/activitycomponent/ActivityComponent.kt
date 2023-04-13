package com.example.daggerseminar.di.activitycomponent

import com.example.daggerseminar.MainActivity
import dagger.Subcomponent

/**
 * @author a.s.korchagin
 */
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ActivityComponent
    }
}
