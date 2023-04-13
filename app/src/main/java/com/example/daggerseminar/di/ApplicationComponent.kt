package com.example.daggerseminar.di

import android.content.Context
import com.example.daggerseminar.PaymentApi
import com.example.daggerseminar.di.activitycomponent.ActivityComponent
import com.example.daggerseminar.di.activitycomponent.ViewModelsModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * @author a.s.korchagin
 */
@Singleton
@Component(modules = [PaymentModule::class, ViewModelsModule::class])
interface ApplicationComponent {
    //fun paymentApi(): PaymentApi
    fun activityComponent(): ActivityComponent.Factory

    fun paymentApi(): PaymentApi

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            context: Context
        ): ApplicationComponent
    }
}
