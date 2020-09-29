package com.makalaster.etherealdialpad.dsp

class ExpEnv: UGen() {
    companion object {
        var hardFactor = 0.005f
        var softFactor = 0.00005f
    }

    private var state = false
    private var attenuation = 0f
    private var factor = softFactor
    private val idealMarker = 0.25f
    private var marker = idealMarker

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
        if (!state && attenuation < 0.0001f) return false
        if (!renderKids(buffer)) return false
        if (state) {
            for (i in 0 until CHUNK_SIZE) {
                buffer[i] *= attenuation
                attenuation += (marker - attenuation) * factor
            }
        } else {
            for (i in 0 until CHUNK_SIZE) {
                buffer[i] *= attenuation
                attenuation += (0 - attenuation) * factor
            }
        }
        return true
    }
}