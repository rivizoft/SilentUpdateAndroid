package com.example.daggerseminar

import okhttp3.OkHttpClient
import okhttp3.Request
import java.math.BigDecimal
import java.util.concurrent.TimeUnit

/**
 * @author a.s.korchagin
 */
class PaymentApi {
    private val url: String = "tinkoff.payment.ru"
    private val client: OkHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()

    fun pay(amount: BigDecimal?) {
        val request: Request = getRequest()
        client.newCall(request).execute()
    }

    private fun getRequest(): Request {
        return Request.Builder()
            .url(url)
            .build()
    }

}
