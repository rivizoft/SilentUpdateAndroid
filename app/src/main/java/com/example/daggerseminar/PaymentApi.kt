package com.example.daggerseminar

import android.content.Context
import com.example.daggerseminar.di.AnotherUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import java.math.BigDecimal
import javax.inject.Inject

/**
 * @author a.s.korchagin
 */
interface PaymentApi {

    fun pay(amount: BigDecimal?)
}
class PaymentApiImpl @Inject constructor(
    private val context: Context,
    private val url: String,
    @AnotherUrl
    private val anotherUrl: String,
    private val client: OkHttpClient
) : PaymentApi {

    override fun pay(amount: BigDecimal?) {
        val request: Request = getRequest()
        client.newCall(request).execute()
    }

    private fun getRequest(): Request {
        return Request.Builder()
            .url(url)
            .build()
    }

}
