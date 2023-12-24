package com.makalaster.etherealdialpad.main

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val _pads: MutableLiveData<Int> = MutableLiveData()
    val pads: LiveData<Int> = _pads

    fun padSelected(@StringRes name: Int) {
        _pads.value = name
    }
}