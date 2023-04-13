package com.example.daggerseminar.di.activitycomponent

import com.example.daggerseminar.MainActivity
import com.example.daggerseminar.PaymentViewModel
import com.example.daggerseminar.di.ActivityScope
import com.example.daggerseminar.di.ApplicationComponent
import dagger.Component

/**
 * @author a.s.korchagin
 */
@ActivityScope
@Component(dependencies = [ApplicationComponent::class],
    modules = [ActivityDependentModule::class])
interface ActivityDependentComponent {

    fun inject(activity: MainActivity)

    fun inject(viewModel: PaymentViewModel)

    @Component.Factory
    interface Factory {
        fun create(
            applicationComponent: ApplicationComponent
        ): ActivityDependentComponent
    }
}
