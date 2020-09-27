package com.makalaster.etherealdialpad.dsp

import kotlin.math.pow
import kotlin.math.sin

class WtOsc: UGen() {
    companion object {
        private const val BITS = 8
        const val ENTRIES = 1 shl (BITS - 1)
        const val MASK = ENTRIES - 1
    }

    private var phase: Float = 0f
    private var cyclesPerSample: Float = 0f

    private val table = FloatArray(ENTRIES)

    @Synchronized
    fun setFreq(freq: Float) {
        cyclesPerSample = freq / SAMPLE_RATE
    }

    override fun render(buffer: FloatArray): Boolean {
        var localPhase = phase
        for (i in 0 until  CHUNK_SIZE) {
            val scaled: Float = localPhase * ENTRIES
            val index: Int = scaled.toInt()
            val fraction: Float = scaled - index
            buffer[i] += (1.0f - fraction) * table[index and MASK]
            localPhase += cyclesPerSample
        }
        phase = localPhase - localPhase.toInt()

        return true
    }

    fun fillWithSin(): WtOsc {
        val dt: Float = (2.0 * Math.PI / ENTRIES).toFloat()
        for (i in 0 until ENTRIES) {
            table[i] = sin(i * dt)
        }

        return this
    }

    fun fillWithHardSin(exp: Float): WtOsc? {
        val dt = (2.0 * Math.PI / ENTRIES).toFloat()
        for (i in 0 until ENTRIES) {
            table[i] = sin(i * dt.toDouble()).pow(exp.toDouble()).toFloat()
        }

        return this
    }

    fun fillWithZero(): WtOsc? {
        for (i in 0 until ENTRIES) {
            table[i] = 0f
        }

        return this
    }

    fun fillWithSqr(): WtOsc? {
        for (i in 0 until ENTRIES) {
            table[i] = if (i < ENTRIES / 2) 1f else -1f
        }

        return this
    }

    fun fillWithSqrDuty(fraction: Float): WtOsc? {
        for (i in 0 until ENTRIES) {
            table[i] = if (i.toFloat() / ENTRIES < fraction) 1f else -1f
        }

        return this
    }

    fun fillWithSaw(): WtOsc? {
        val dt = (2.0 / ENTRIES).toFloat()
        for (i in 0 until ENTRIES) {
            table[i] = 1.0f - i * dt
        }

        return this
    }
}