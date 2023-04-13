package com.example.daggerseminar.di

import com.example.daggerseminar.PaymentApi
import com.example.daggerseminar.PaymentApiImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author a.s.korchagin
 */
@Module(includes = [PaymentModule.Bind::class])
class PaymentModule {

    @Provides
    fun provideUrl(): String = "tinkoff.payment.ru"

    @Provides
    @AnotherUrl
    fun provideAnotherUrl(): String = "tinkoff2.payment.ru"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()

    @Module
    abstract class Bind {
        @Binds
        abstract fun bindsPaymentApi(impl: PaymentApiImpl): PaymentApi
    }
}
