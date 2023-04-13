package com.example.daggerseminar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 * @author a.s.korchagin
 */
@Suppress("UNCHECKED_CAST")
open class ViewModelFactory @Inject constructor(
    private val viewModels: @JvmSuppressWildcards Map<String, Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val viewModelProvider = viewModels[modelClass::class.java.simpleName] ?: error("ViewModel class $modelClass not found")
        return viewModelProvider.get() as T
    }
}
