package com.makalaster.etherealdialpad.pads

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makalaster.etherealdialpad.dsp.Synth
import com.makalaster.etherealdialpad.prefs.PrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class PadViewModel @Inject constructor(
    protected val prefsRepo: PrefsRepository,
    protected val synth: Synth
) : ViewModel() {
    private var job: Job? = null

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

    override fun onCleared() {
        job?.cancel()
        job = null

        synth.stop()

        super.onCleared()
    }
}