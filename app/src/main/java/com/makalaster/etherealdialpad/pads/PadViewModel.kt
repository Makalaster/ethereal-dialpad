package com.makalaster.etherealdialpad.pads

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makalaster.etherealdialpad.dsp.Synth
import com.makalaster.etherealdialpad.prefs.PadSettings
import com.makalaster.etherealdialpad.prefs.PrefsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class PadViewModel(
    protected val prefsRepo: PrefsRepository,
    protected val synth: Synth
) : ViewModel() {
    private var job: Job? = null

    protected abstract val pad: String
    abstract val flow: StateFlow<PadSettings>

    init {
        synth.start()
        job = viewModelScope.launch(Dispatchers.IO) {
            while (job?.isCancelled != true) {
                synth.tick()
            }
        }
    }

    fun primaryOn() {
        synth.primaryOn()
    }

    fun primaryXY(x: Float, y: Float) {
        synth.primaryXY(x, y)
    }

    fun primaryOff() {
        synth.primaryOff()
    }

//    fun secondaryOn() {
//        ugEnvB.setActive(true)
//    }
//
//    fun secondaryXY(ctx: Context, x: Float, y: Float) {
//        val scale = buildScale(prefsRepo.getQuantizer(ctx))
//        val octaves = prefsRepo.getOctaves(ctx)
//
//        ugOscB1.setFreq(buildFrequency(scale, octaves, x))
//        if (prefsRepo.isDuetEnabled(ctx)) {
//            ugOscB2.setFreq(buildFrequency(scale, octaves, y))
//        } else {
//            ugEnvB.setGain(2 * y * y)
//        }
//    }
//
//    fun secondaryOff() {
//        ugEnvB.setActive(false)
//    }

    fun updateBooleanPref(pref: String, value: Boolean) {
        viewModelScope.launch {
            prefsRepo.setBooleanPref(pad, pref, value)
        }
    }

    fun updateStringPref(pref: String, selection: String) {
        viewModelScope.launch {
            prefsRepo.setStringPref(pad, pref, selection)
        }
    }

    override fun onCleared() {
        job?.cancel()
        job = null

        synth.stop()

        super.onCleared()
    }
}