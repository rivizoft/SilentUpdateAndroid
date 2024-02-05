package com.example.daggerseminar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author k.shiryaev
 */
class MainViewModel : ViewModel() {

    private val _backgroundState: MutableLiveData<Boolean> = MutableLiveData()
    val backgroundState: LiveData<Boolean> = _backgroundState

    fun backgroundModeChanged(isAllow: Boolean) {
        _backgroundState.value = isAllow
    }

}
