package com.makalaster.etherealdialpad.dsp

class Flange(length: Int, freq: Float): UGen() {
    var line: FloatArray = FloatArray(length)
    var phase = 0f
    var cyclesPerSample = freq / SAMPLE_RATE
    var pointer = 0

    override fun render(buffer: FloatArray): Boolean {
        renderKids(buffer)
        var localPhase = phase
        var localPointer = pointer
        val length = line.size
        for (i in 0 until CHUNK_SIZE) {
            line[localPointer] = buffer[i]
            val samplePointer =
                (localPointer + length * (1.0f - localPhase * (1.0f - localPhase))).toInt() % length
            buffer[i] = 0.5f * buffer[i] - 0.5f * line[samplePointer]
            localPointer = (localPointer + 1) % length
            localPhase += cyclesPerSample
            localPhase -= localPhase.toInt()
        }
        pointer = localPointer
        phase = localPhase
        return true
    }
}