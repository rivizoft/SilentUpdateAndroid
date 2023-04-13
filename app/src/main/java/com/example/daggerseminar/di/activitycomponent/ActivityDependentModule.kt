package com.example.daggerseminar.di.activitycomponent

import com.example.daggerseminar.PaymentRepository2
import com.example.daggerseminar.PaymentRepositoryImpl2
import dagger.Binds
import dagger.Module

/**
 * @author a.s.korchagin
 */
@Module
interface ActivityDependentModule {

    @Binds
    fun bindsPaymentRepository(impl: PaymentRepositoryImpl2): PaymentRepository2
}
