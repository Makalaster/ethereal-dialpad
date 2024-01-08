package com.makalaster.ethereal_dialpad.pads.grid

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.RemoteException
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceView

class GridView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
):
    SurfaceView(context, attrs, defStyleAttr) {
    init {
        setBackgroundColor(-0xbfbfc0)
    }
    private var synthService: ISynthService? = null

    private var sw = 0
    private var sh:Int = 0
    private var nx = 0
    private var ny:Int = 0
    private lateinit var scaleX: FloatArray
    private lateinit var scaleY: FloatArray
    private lateinit var colsX: IntArray
    private lateinit var colsY: IntArray
    private var u = 0f
    private var v: Float = 0f
    private var paint = Paint()
    private var hsv = FloatArray(3)
    private var touching = false
    private lateinit var scale: FloatArray
    private var octaves = 0
    private var duet = false

    override fun onSizeChanged(w: Int, h: Int, ow: Int, oh: Int) {
        sw = w
        sh = h
    }

    /**
     * We are bound to the synth service.
     */
    fun onConnected() {
        try {
            scale = synthService!!.scale
            octaves = synthService!!.octaves
            duet = synthService!!.isDuet
            nx = scale.size * octaves + 1
            ny = if (duet) nx else 0
            scaleX = scale

            colsX = IntArray(scaleX.size)
            for (i in scaleX.indices) {
                colsX[i] = noteColor(scaleX[i])
            }

            scaleY = if (duet) scale else FloatArray(0)
            if (scaleY.isEmpty()) colsY = IntArray(0) else {
                colsY = IntArray(scaleY.size)
                for (i in scaleY.indices)
                    colsY[i] = noteColor(scaleY[i])
            }
        } catch (e: RemoteException) {
            ny = 0
            nx = ny
            scaleY = FloatArray(0)
            scaleX = scaleY
            colsY = IntArray(0)
            colsX = colsY
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        u = event.x / sw
        v = 1.0f - event.y / sh
        if (event.action == MotionEvent.ACTION_DOWN) {
            touching = true
            try {
                synthService!!.primaryOn()
                synthService!!.primaryXY(u, v)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
        if (event.action == MotionEvent.ACTION_UP) {
            touching = false
            try {
                synthService!!.primaryOff()
                synthService!!.primaryXY(u, v)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
        if (event.action == MotionEvent.ACTION_MOVE) {
            try {
                synthService!!.primaryXY(u, v)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas) {
        // Draw a background colour based on the most recent hue.
        val r = 128f + 128f * (u - 0.5f)
        val g: Float = 128f + 128f * (v - 0.5f)
        val b = 128f + 128f * (0.5f - u)
        if (touching) {
            canvas.drawARGB(255, r.toInt(), g.toInt(), b.toInt())
        }

        // Draw the "strings".
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 1.5f
        if (sw > 0 && nx > 0) {
            val nw = sw / nx.toFloat()
            for (s in 0 until nx) {
                val x = s * nw + nw / 2f
                paint.color = colsX[s % colsX.size]
                canvas.drawLine(x, 0f, x, sh.toFloat(), paint)
            }
        }
        if (sh > 0 && ny > 0) {
            val nh: Float = sh / ny.toFloat()
            for (s in 0 until ny) {
                var y = s * nh + nh / 2f
                y = sh - y
                paint.color = colsY[s % colsY.size]
                canvas.drawLine(0f, y, sw.toFloat(), y, paint)
            }
        }
        if (touching) {
            // 	Draw the user's finger.
            paint.style = Paint.Style.FILL
            paint.color = -0x7f000001
            paint.isAntiAlias = true
            val cx = sw * (0.5f + (u * nx).toInt()) / nx
            val cy: Float = if (duet) sh * (0.5f + ((1 - v) * ny)) / ny else (1 - v) * sh
            canvas.drawCircle(cx, cy, sw / (2 * nx).toFloat(), paint)
        }
    }

    private fun noteColor(n: Float): Int {
        val a = 255
        hsv[0] = n / 12f * 300f
        hsv[1] = if (n == 0f) 0f else 1f
        hsv[2] = 1f
        return Color.HSVToColor(a, hsv)
    }
}