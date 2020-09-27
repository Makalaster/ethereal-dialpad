package com.makalaster.etherealdialpad.dsp

class ExpEnv: UGen() {
    companion object {
        val hardFactor = 0.005f
        val softFactor = 0.00005f
    }

    var state: Boolean = false
    var attenuation: Float = 0f
    var factor = softFactor
    val idealMarker = 0.25f
    var marker = idealMarker

    @Synchronized
    fun setActive(nextState: Boolean) {
        state = nextState
    }

    @Synchronized
    fun setLocalFactor(nextFactor: Float) {
        factor = nextFactor
    }

    @Synchronized
    fun setGain(gain: Float) {
        marker = gain * idealMarker
    }

    @Synchronized
    override fun render(buffer: FloatArray): Boolean {
        if ((!state && attenuation < 0.0001f) || !renderKids(buffer)) return false

        for (i in 0 until CHUNK_SIZE) {
            buffer[i] *= attenuation
            attenuation += (if (state) marker else 0 - attenuation) * factor
        }

        return true
    }
}