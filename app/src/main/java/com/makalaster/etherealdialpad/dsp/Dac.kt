package com.makalaster.etherealdialpad.dsp

import android.media.AudioAttributes
import android.media.AudioAttributes.CONTENT_TYPE_MUSIC
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import kotlin.math.max

class Dac: UGen() {
    private var localBuffer = FloatArray(CHUNK_SIZE)
    private var isClean = false
    private var track: AudioTrack
    private val target = ShortArray(CHUNK_SIZE)
    private val silentTarget = ShortArray(CHUNK_SIZE)

    init {
        val minSize = AudioTrack.getMinBufferSize(
            SAMPLE_RATE,
            AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        )

        val audioAttributes = AudioAttributes.Builder()
            .setLegacyStreamType(AudioManager.STREAM_MUSIC)
            .setContentType(CONTENT_TYPE_MUSIC)
            .build()

        val audioFormat = AudioFormat.Builder()
            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
            .setSampleRate(SAMPLE_RATE)
            .build()

        track = AudioTrack.Builder()
            .setAudioAttributes(audioAttributes)
            .setAudioFormat(audioFormat)
            .setBufferSizeInBytes(max(CHUNK_SIZE * 4, minSize))

            .build()
    }

    override fun render(buffer: FloatArray): Boolean {
        if (!isClean) {
            zeroBuffer(localBuffer)
            isClean = true
        }
        // localBuffer is always clean right here, does it stay that way?
        isClean = !renderKids(localBuffer)
        return !isClean // we did some work if the buffer isn't clean
    }

    fun open() {
        track.play()
    }

    fun tick() {
        render(localBuffer)
        if (isClean) {
            // sleeping is messy, so lets just queue this silent buffer
            track.write(silentTarget, 0, silentTarget.size)
        } else {
            for (i in 0 until CHUNK_SIZE) {
                target[i] = (32768.0f * localBuffer[i]).toInt().toShort()
            }
            track.write(target, 0, target.size)
        }
    }

    fun close() {
        track.stop()
        track.release()
    }
}