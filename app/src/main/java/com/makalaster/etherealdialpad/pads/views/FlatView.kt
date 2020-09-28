package com.makalaster.etherealdialpad.pads.views

import ISynthService
import android.content.Context
import android.os.RemoteException
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceView
import kotlin.math.max
import kotlin.math.min

class FlatView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0): SurfaceView(context, attrs, defStyleAttr) {
    init {
        setBackgroundColor(-0xbfbfc0)
    }

    private var synthService: ISynthService? = null

    fun setSynthService(iSynthService: ISynthService?) {
        synthService = iSynthService
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val u = min(1f, max(0f, event!!.x / width))
        val v = min(1f, max(0f, 1.0f - event.y / height))

        if (event.action == MotionEvent.ACTION_UP) {
            try {
                synthService?.primaryOff()
                synthService?.primaryXY(u, v)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
            setBackgroundColor(-0xbfbfc0)
            invalidate()
        } else {
            if (event.action == MotionEvent.ACTION_DOWN) {
                try {
                    synthService?.primaryOn()
                    synthService?.primaryXY(u, v)
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
            } else if (event.action == MotionEvent.ACTION_MOVE) {
                try {
                    synthService?.primaryXY(u, v)
                } catch (e: RemoteException) {
                    e.printStackTrace()
                }
            }
            val r = (128 + 128 * (u - 0.5f)).toInt()
            val g = (128 + 128 * (v - 0.5f)).toInt()
            val b = (128 + 128 * (0.5f - u)).toInt()
            setBackgroundColor(-0x1000000 or (r shl 16) or (g shl 8) or b)
            invalidate()
        }

        return true
    }
}