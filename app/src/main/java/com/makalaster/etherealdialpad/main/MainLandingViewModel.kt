package com.makalaster.etherealdialpad.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.makalaster.etherealdialpad.R
import com.makalaster.etherealdialpad.pads.Pad

class MainLandingViewModel : ViewModel() {
    private val _pads: MutableLiveData<List<Pad>> = MutableLiveData()
    val pads: LiveData<List<Pad>> = _pads

    fun generatePads() {
        val padsList = mutableListOf<Pad>()

        padsList.add(Pad(R.drawable.basicpad_icon, R.string.basicpad_label))
        padsList.add(Pad(R.drawable.trailpad_icon, R.string.trailpad_label))
        padsList.add(Pad(R.drawable.pointpad_icon, R.string.pointpad_label))
        padsList.add(Pad(R.drawable.gridpad_icon, R.string.gridpad_label))

        _pads.value = padsList
    }
}