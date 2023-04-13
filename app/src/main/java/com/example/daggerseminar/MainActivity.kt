package com.example.daggerseminar

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.daggerseminar.di.activitycomponent.DaggerActivityDependentComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var paymentApi: PaymentApi
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val viewModel: PaymentViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
//        getAppComponent().activityComponent()
//            .create()
//            .inject(this)
        DaggerActivityDependentComponent.factory()
            .create(getAppComponent())
            .inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
