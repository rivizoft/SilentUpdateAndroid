package com.example.daggerseminar.di.activitycomponent

import com.example.daggerseminar.PaymentRepository
import com.example.daggerseminar.PaymentRepositoryImpl
import dagger.Binds
import dagger.Module

/**
 * @author a.s.korchagin
 */
@Module
interface ActivityModule {

    @Binds
    fun bindsPaymentRepository(impl: PaymentRepositoryImpl): PaymentRepository
}
