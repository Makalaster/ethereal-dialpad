package com.makalaster.etherealdialpad.dsp

import com.makalaster.etherealdialpad.prefs.PadSettings
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

class Synth {
    private var settings = PadSettings()

    private val ugOscA1 = WtOsc()
    private val ugOscA2 = WtOsc()
    private var ugEnvA = ExpEnv()

    private val ugOscB1 = WtOsc()
    private val ugOscB2 = WtOsc()
    private var ugEnvB = ExpEnv()

    private val ugDac = Dac()

    fun start() {
        ugDac.open()
    }

    fun tick() {
        ugDac.tick()
    }

    fun refreshSettings(newSettings: PadSettings) {
        ugEnvA = ExpEnv()
        ugEnvB = ExpEnv()

        if (newSettings.softTimbre) {
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

        if (newSettings.delay) {
            ugEnvA.chuck(ugDelay)
            ugEnvB.chuck(ugDelay)
            if (newSettings.flange) {
                ugDelay.chuck(ugFlange).chuck(ugDac)
            } else {
                ugDelay.chuck(ugDac)
            }
        } else {
            if (newSettings.flange) {
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

        if (!newSettings.softEnvelope) {
            ugEnvA.setLocalFactor(ExpEnv.hardFactor)
            ugEnvB.setLocalFactor(ExpEnv.hardFactor)
        }

        settings = newSettings
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

    fun primaryXY(x: Float, y: Float) {
        val scale = buildScale(settings.quantizer.value)
        val octaves = settings.octaves.toInt()

        ugOscA1.setFreq(buildFrequency(scale, octaves, x))
        if (settings.duet) {
            ugOscA2.setFreq(buildFrequency(scale, octaves, y))
        } else {
            ugEnvA.setGain(2 * y * y)
        }
    }

    fun primaryOff() {
        ugEnvA.setActive(false)
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

    fun stop() {
        ugDac.pause()
    }
}