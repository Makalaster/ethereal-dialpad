package com.makalaster.etherealdialpad.dsp

class Flange(length: Int, freq: Float): UGen() {
    private val line: FloatArray = FloatArray(length)
    private var phase = 0f
    private val cyclesPerSample: Float = freq / SAMPLE_RATE
    private var pointer = 0

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
            localPhase -= localPhase
        }
        pointer = localPointer
        phase = localPhase

        return true
    }
}