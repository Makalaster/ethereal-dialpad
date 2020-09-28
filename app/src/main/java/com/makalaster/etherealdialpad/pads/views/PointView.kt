package com.makalaster.etherealdialpad.pads.views

import ISynthService
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.RemoteException
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class PointView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0):
    View(context, attrs, defStyleAttr) {
    companion object {
        private const val N = 100
    }

    init {
        setBackgroundColor(-0xefefe0)
        keepScreenOn = true
        isFocusable = true
    }

    private var synthService: ISynthService? = null

    fun setSynthService(iSynthService: ISynthService?) {
        synthService = iSynthService
    }

    private val spotPaint = Paint().apply {
        setARGB(255, 255, 200, 64)
        style = Paint.Style.STROKE
        strokeWidth = 2f
        isAntiAlias = true
    }
    private var manualOffset = 0
    private var lastTick: Long = 0
    private var xCoord = 0f
    private var yCoord = 0f
    private var targetX = 0f
    private var targetY:Float = 0f
    private var isTouching = false

    private val lines: FloatArray = FloatArray(4 * N)

    private fun updateLines() {
        val k = 0.975f
        val tx = (1.0f - k) * targetX
        val ty: Float = (1.0f - k) * targetY
        val f = 0.875f
        val w = width.toFloat()
        val h = height.toFloat()
        val d = 0.025f
        for (i in 0 until N) {
            val px = lines[4 * i + 0]
            val py = lines[4 * i + 1]
            if (isTouching) {
                lines[4 * i + 0] = lines[4 * i + 2]
                lines[4 * i + 1] = lines[4 * i + 3]
                lines[4 * i + 2] = k * lines[4 * i + 2] + tx + (lines[4 * i + 2] - px) * f
                lines[4 * i + 3] = k * lines[4 * i + 3] + ty + (lines[4 * i + 3] - py) * f
            } else {
                lines[4 * i + 0] = lines[4 * i + 2]
                lines[4 * i + 1] = lines[4 * i + 3]
                lines[4 * i + 2] += (lines[4 * i + 0] - px) * f
                lines[4 * i + 3] += (lines[4 * i + 1] - py) * f
            }
            if (Math.random() < 0.1) {
                lines[4 * i + 0] = w * Math.random().toFloat()
                lines[4 * i + 1] = h * Math.random().toFloat()
                lines[4 * i + 2] = lines[4 * i + 0] + d * w * (Math.random() - 0.5).toFloat()
                lines[4 * i + 3] = lines[4 * i + 1] + d * h * (Math.random() - 0.5).toFloat()
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val ex = event.x
        val ey = event.y
        targetX = ex
        targetY = ey
        xCoord = ex / width
        yCoord = 1 - ey / height
        if (event.action == MotionEvent.ACTION_MOVE) {
            try {
                synthService?.primaryXY(xCoord, yCoord)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
        if (event.action == MotionEvent.ACTION_DOWN) {
            isTouching = true
            try {
                synthService?.primaryOn()
                synthService?.primaryXY(xCoord, yCoord)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
        if (event.action == MotionEvent.ACTION_UP) {
            isTouching = false
            try {
                synthService?.primaryOff()
                synthService?.primaryXY(xCoord, yCoord)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas) {
        updateLines()
        canvas.drawLines(lines, spotPaint)
        invalidate()
    }
}