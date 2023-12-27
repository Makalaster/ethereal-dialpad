package com.makalaster.etherealdialpad.pads

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makalaster.etherealdialpad.dsp.Dac
import com.makalaster.etherealdialpad.dsp.Delay
import com.makalaster.etherealdialpad.dsp.ExpEnv
import com.makalaster.etherealdialpad.dsp.Flange
import com.makalaster.etherealdialpad.dsp.UGen
import com.makalaster.etherealdialpad.dsp.WtOsc
import com.makalaster.etherealdialpad.prefs.PrefsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

class PadViewModel : ViewModel() {
    private var job: Job? = null

    private val ugOscA1 = WtOsc()
    private val ugOscA2 = WtOsc()
    private val ugEnvA = ExpEnv()

    private val ugOscB1 = WtOsc()
    private val ugOscB2 = WtOsc()
    private val ugEnvB = ExpEnv()

    private val ugDac = Dac()

    init {
        ugDac.open()
        job = viewModelScope.launch(Dispatchers.IO) {
            while (job?.isCancelled != true) {
                ugDac.tick()
            }
            ugDac.close()
        }
    }

    fun refreshPrefs(ctx: Context) {
        if (PrefsRepository.isSoftTimbreEnabled(ctx)) {
            ugOscA1.fillWithHardSin(7.0f)
            ugOscB1.fillWithHardSin(7.0f)
            ugOscA2.fillWithHardSin(2.0f)
            ugOscB2.fillWithHardSin(2.0f)
        } else {
            ugOscA1.fillWithSqrDuty(0.6f)
            ugOscB1.fillWithSqrDuty(0.6f)
            ugOscA2.fillWithSqrDuty(0.9f)
            ugOscB2.fillWithSqrDuty(0.9f)
        }

        val ugDelay = Delay(UGen.SAMPLE_RATE / 2)
        val ugFlange = Flange(UGen.SAMPLE_RATE / 64, 0.25f)

        if (PrefsRepository.isDelayEnabled(ctx)) {
            ugEnvA.chuck(ugDelay)
            ugEnvB.chuck(ugDelay)
            if (PrefsRepository.isFlangeEnabled(ctx)) {
                ugDelay.chuck(ugFlange).chuck(ugDac)
            } else {
                ugDelay.chuck(ugDac)
            }
        } else {
            if (PrefsRepository.isFlangeEnabled(ctx)) {
                ugEnvA.chuck(ugFlange)
                ugEnvB.chuck(ugFlange)
                ugFlange.chuck(ugDac)
            } else {
                ugEnvA.chuck(ugDac)
                ugEnvB.chuck(ugDac)
            }
        }

        ugOscA1.chuck(ugEnvA)
        ugOscB1.chuck(ugEnvB)
        ugOscA2.chuck(ugEnvA)
        ugOscB2.chuck(ugEnvB)

        if (!PrefsRepository.isSoftEnvelopeEnabled(ctx)) {
            ugEnvA.setLocalFactor(ExpEnv.hardFactor)
            ugEnvB.setLocalFactor(ExpEnv.hardFactor)
        }
    }

    private fun buildScale(quantizerString: String?): FloatArray? {
        return if (!quantizerString.isNullOrEmpty()) {
            val parts = quantizerString.split(",".toRegex()).toTypedArray()
            val scale = FloatArray(parts.size)
            for (i in parts.indices) {
                scale[i] = parts[i].toFloat()
            }
            return scale
        } else {
            null
        }
    }

    private fun buildFrequency(scale: FloatArray?, octaves: Int, input: Float): Float {
        val variableInput: Float = min(max(input, 0.0f), 1.0f)
        val base = 48f
        val mapped: Float = if (scale == null) {
            base + variableInput * octaves * 12.0f
        } else {
            val idx = ((scale.size * octaves + 1) * variableInput).toInt()
            base + scale[idx % scale.size] + 12 * (idx / scale.size)
        }
        return 2.0f.pow((mapped - 69.0f) / 12.0f) * 440.0f
    }

    fun primaryOn() {
        ugEnvA.setActive(true)
    }

    fun primaryXY(ctx: Context, x: Float, y: Float) {
        val scale = buildScale(PrefsRepository.getQuantizer(ctx))
        val octaves = PrefsRepository.getOctaves(ctx)

        ugOscA1.setFreq(buildFrequency(scale, octaves, x))
        if (PrefsRepository.isDuetEnabled(ctx)) {
            ugOscA2.setFreq(buildFrequency(scale, octaves, y))
        } else {
            ugEnvA.setGain(2 * y * y)
        }
    }

    fun primaryOff() {
        ugEnvA.setActive(false)
    }

    fun secondaryOn() {
        ugEnvB.setActive(true)
    }

    fun secondaryXY(ctx: Context, x: Float, y: Float) {
        val scale = buildScale(PrefsRepository.getQuantizer(ctx))
        val octaves = PrefsRepository.getOctaves(ctx)

        ugOscB1.setFreq(buildFrequency(scale, octaves, x))
        if (PrefsRepository.isDuetEnabled(ctx)) {
            ugOscB2.setFreq(buildFrequency(scale, octaves, y))
        } else {
            ugEnvB.setGain(2 * y * y)
        }
    }

    fun secondaryOff() {
        ugEnvB.setActive(false)
    }

    override fun onCleared() {
        job?.cancel()
        job = null

        super.onCleared()
    }
}