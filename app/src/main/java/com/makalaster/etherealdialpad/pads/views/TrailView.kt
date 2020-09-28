package com.makalaster.etherealdialpad.pads.views

import ISynthService
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.os.RemoteException
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.collections.ArrayList
import kotlin.math.sqrt

class TrailView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0):
    View(context, attrs, defStyleAttr) {
    init {
        setBackgroundColor(-0x1000000)
        keepScreenOn = true
        isFocusable = true
    }

    private var synthService: ISynthService? = null

    fun setSynthService(iSynthService: ISynthService?) {
        synthService = iSynthService
    }

    private val xHistory: MutableList<Float> = ArrayList()
    private val yHistory: MutableList<Float> = ArrayList()
    private val path = Path()
    private val pathPaint = Paint().apply {
        setARGB(255, 255, 255, 255)
        style = Paint.Style.STROKE
        strokeWidth = 4f
        setShadowLayer(6f, 0f, 0f, -0x1)
        style = Paint.Style.FILL
    }
    private val fingerPaint = Paint().apply {
        setARGB(128, 255, 255, 255)
        setShadowLayer(10f, 0f, 0f, -0x1)
    }

    private var manualOffset = 0

    private var lastTick: Long = 0
    private var xCoord = 0f
    private var yCoord = 0f
    private var isTouching = false

    private var lastX = 0f
    private var lastY = 0f
    private var d = 0f
    private val maxD = 512f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val ex = event.x
        val ey = event.y
        xCoord = ex / width
        yCoord = 1 - ey / height
        if (event.action == MotionEvent.ACTION_MOVE) {
            try {
                synthService?.primaryXY(xCoord, yCoord)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
            xHistory.add(ex)
            yHistory.add(ey)
            d += sqrt((ex - lastX) * (ex - lastX) + (ey - lastY) * (ey - lastY).toDouble())
                .toFloat()
            while (d > maxD && xHistory.size > 1) {
                val surplus = d - maxD
                val ax = xHistory[0]
                val ay = yHistory[0]
                val bx = xHistory[1]
                val by = yHistory[1]
                val chopD =
                    sqrt((ax - bx) * (ax - bx) + (ay - by) * (ay - by).toDouble()).toFloat()
                if (surplus > chopD) {
                    xHistory.removeAt(0)
                    yHistory.removeAt(0)
                    d -= chopD
                } else {
                    val frac = surplus / chopD
                    val nx = (1 - frac) * ax + frac * bx
                    val ny = (1 - frac) * ay + frac * by
                    xHistory[0] = nx
                    yHistory[0] = ny
                    d -= surplus
                    break
                }
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
            xHistory.clear()
            yHistory.clear()
            xHistory.add(ex)
            yHistory.add(ey)
            d = 0f
        }
        if (event.action == MotionEvent.ACTION_UP) {
            isTouching = false
            try {
                synthService?.primaryOff()
                synthService?.primaryXY(xCoord, yCoord)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
            xHistory.clear()
            yHistory.clear()
            d = 0f
        }
        lastX = ex
        lastY = ey
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas) {
        path.rewind()
        if (xHistory.size > 0) {
            path.moveTo(xHistory[0], yHistory[0])
        }
        val steps = xHistory.size - 1
        for (i in 0 until steps) {
            val xA = xHistory[i]
            val yA = yHistory[i]
            val xB = xHistory[i + 1]
            val yB = yHistory[i + 1]
            val xP = (xA + xB) / 2
            val yP = (yA + yB) / 2
            path.quadTo(
                xA, yA,
                xP, yP
            )
        }
        if (xHistory.size > 0) {
            path.lineTo(xHistory[steps], yHistory[steps])
        }
        canvas.drawPath(path, pathPaint)
        if (isTouching) {
            canvas.drawCircle(lastX, lastY, 15f, fingerPaint)
        }
    }
}