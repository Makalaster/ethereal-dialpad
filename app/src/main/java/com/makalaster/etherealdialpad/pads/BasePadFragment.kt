package com.makalaster.etherealdialpad.pads

import ISynthService
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.fragment.app.Fragment
import com.makalaster.etherealdialpad.dsp.SynthService

abstract class BasePadFragment : Fragment() {
    protected var synthService: ISynthService? = null
    private val synthServiceConnection = SynthServiceConnection()

    override fun onResume() {
        super.onResume()

        activity?.applicationContext?.bindService(
            Intent(requireContext(), SynthService::class.java),
            synthServiceConnection,
            Context.BIND_AUTO_CREATE)
    }

    override fun onPause() {
        super.onPause()
        activity?.applicationContext?.unbindService(synthServiceConnection)
    }

    inner class SynthServiceConnection: ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            synthService = ISynthService.Stub.asInterface(service)
            onSynthServiceConnected()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            synthService = null
            onSynthServiceDisconnected()
        }
    }

    protected open fun onSynthServiceConnected() {}
    protected open fun onSynthServiceDisconnected() {}
}