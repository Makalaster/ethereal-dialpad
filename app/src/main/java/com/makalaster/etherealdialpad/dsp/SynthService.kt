package com.makalaster.etherealdialpad.dsp

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Process
import android.util.Log
import androidx.preference.PreferenceManager
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

class SynthService: Service() {
    companion object {
        const val TAG: String = "SynthService"

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
    }

    private var thread: Thread? = null

    override fun onBind(intent: Intent?): IBinder? {
        val synthPrefs = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        val scale = buildScale(synthPrefs.getString("quantizer", "1,4,6,9,11"))
        val octaves = synthPrefs.getString("octaves", "3")!!.toInt()

        val delay = synthPrefs.getBoolean("delay", true)
        val flange = synthPrefs.getBoolean("flange", false)
        val softTimbre = synthPrefs.getBoolean("softTimbre", true)
        val softEnvelope = synthPrefs.getBoolean("softEnvelope", true)
        val isDuet = synthPrefs.getBoolean("duet", true)

        val ugOscA1 = WtOsc()
        val ugOscA2 = WtOsc()
        val ugEnvA = ExpEnv()

        val ugOscB1 = WtOsc()
        val ugOscB2 = WtOsc()
        val ugEnvB = ExpEnv()

        if (softTimbre) {
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

        val ugDac = Dac()
        val ugDelay = Delay(UGen.SAMPLE_RATE / 2)
        val ugFlange = Flange(UGen.SAMPLE_RATE / 64, 0.25f)

        if (delay) {
            ugEnvA.chuck(ugDelay)
            ugEnvB.chuck(ugDelay)
            if (flange) {
                ugDelay.chuck(ugFlange).chuck(ugDac)
            } else {
                ugDelay.chuck(ugDac)
            }
        } else {
            if (flange) {
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

        if (!softEnvelope) {
            ugEnvA.setLocalFactor(ExpEnv.hardFactor)
            ugEnvB.setLocalFactor(ExpEnv.hardFactor)
        }

        if (thread != null) {
            Log.e(
                TAG,
                "Someone tried to bind when there was already an audio thread!",
                RuntimeException()
            )
        }

        thread = object : Thread() {
            override fun run() {
                Log.d(TAG, "started audio rendering")
                System.gc()
                Process.setThreadPriority(Process.THREAD_PRIORITY_AUDIO)
                ugDac.open()
                while (!isInterrupted) {
                    ugDac.tick()
                }
                ugDac.close()
                Log.d(TAG, "finished audio rendering")
            }
        }

        thread?.start()

        return object : ISynthService.Stub() {
            override fun getScale(): FloatArray? {
                return scale
            }

            override fun isDuet(): Boolean {
                return isDuet
            }

            override fun getOctaves(): Int {
                return octaves
            }

            override fun primaryOff() {
                ugEnvA.setActive(false)
            }

            override fun primaryOn() {
                ugEnvA.setActive(true)
            }

            override fun primaryXY(x: Float, y: Float) {
                ugOscA1.setFreq(buildFrequency(scale, octaves, x))
                if (isDuet) {
                    ugOscA2.setFreq(buildFrequency(scale, octaves, y))
                } else {
                    ugEnvA.setGain(2 * y * y)
                }
            }

            override fun secondaryOff() {
                ugEnvB.setActive(false)
            }

            override fun secondaryOn() {
                ugEnvB.setActive(true)
            }

            override fun secondaryXY(x: Float, y: Float) {
                ugOscB1.setFreq(buildFrequency(scale, octaves, x))
                if (isDuet) {
                    ugOscB2.setFreq(buildFrequency(scale, octaves, y))
                } else {
                    ugEnvB.setGain(2 * y * y)
                }
            }
        }
    }

    override fun onDestroy() {
        thread?.interrupt()
        try {
            thread?.join()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}