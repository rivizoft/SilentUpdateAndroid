package com.example.daggerseminar

import java.math.BigDecimal
import javax.inject.Inject

/**
 * @author a.s.korchagin
 */
interface PaymentRepository {

    fun load()
}

class PaymentRepositoryImpl @Inject constructor(
    private val paymentApi: PaymentApi
) : PaymentRepository {

    override fun load() {
        paymentApi.pay(BigDecimal.ONE)
    }
}

interface PaymentRepository2 {

    fun load()
}

class PaymentRepositoryImpl2 @Inject constructor(
    private val paymentApi: PaymentApi
) : PaymentRepository2 {

    override fun load() {
        paymentApi.pay(BigDecimal.ONE)
    }
}
