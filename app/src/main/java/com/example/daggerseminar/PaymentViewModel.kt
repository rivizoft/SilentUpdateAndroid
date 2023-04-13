package com.example.daggerseminar

import androidx.lifecycle.ViewModel
import javax.inject.Inject

/**
 * @author a.s.korchagin
 */
class PaymentViewModel  constructor(

) : ViewModel() {

    @Inject
    lateinit var paymentRepository2: PaymentRepository2

}
