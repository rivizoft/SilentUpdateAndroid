package com.example.daggerseminar.di.activitycomponent

import androidx.lifecycle.ViewModel
import com.example.daggerseminar.PaymentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey

/**
 * @author a.s.korchagin
 */
@Module
interface ViewModelsModule {


    @IntoMap
    @StringKey("PaymentViewModel")
    @Binds
    fun bindsPaymentViewModel(impl: PaymentViewModel): ViewModel

}
