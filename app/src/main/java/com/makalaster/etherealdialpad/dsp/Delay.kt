package com.makalaster.etherealdialpad.dsp

class Delay(length: Int): UGen() {
    private val line: FloatArray = FloatArray(length)
    var pointer: Int = 0

    override fun render(buffer: FloatArray): Boolean {
        renderKids(buffer)

        val localLine: FloatArray = line
        val lineLength = line.size
        for (i in 0 until CHUNK_SIZE) {
            buffer[i] = buffer[i] - 0.5f * localLine[pointer]
            localLine[pointer] = buffer[i]
            pointer = (pointer + 1) % lineLength
        }

        /*
            ugh, looks like we can never be sure it's silent
            without checking every sample because of the feedback
         */
        return true
    }
}