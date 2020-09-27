package com.makalaster.etherealdialpad.dsp

abstract class UGen {
    companion object {
        const val CHUNK_SIZE = 256
        const val SAMPLE_RATE = 22050
    }

    val kids = ArrayList<UGen>()

    /**
        fill CHUNK_SIZE samples
        and return true if you actually did any work
    */
    abstract fun render(buffer: FloatArray): Boolean

    @Synchronized
    fun chuck(ugen: UGen): UGen {
        if (!ugen.kids.contains(this)) {
            ugen.kids.add(this)
        }

        return ugen
    }

    @Synchronized
    fun unchuck(ugen: UGen): UGen {
        if (ugen.kids.contains(this)) {
            ugen.kids.remove(this)
        }

        return ugen
    }

    protected fun zeroBuffer(buffer: FloatArray) {
        for (i in 0 until CHUNK_SIZE) {
            buffer[i] = 0f
        }
    }

    protected fun renderKids(buffer: FloatArray): Boolean {
        var didSomeRealWork = false

        for (i in 0 until kids.size) {
            didSomeRealWork = didSomeRealWork || kids[i].render(buffer)
        }

        return didSomeRealWork
    }
}