package com.makalaster.ethereal_dialpad.pads.grid

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
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
    private lateinit var colsY: IntArray
    private var u = 0f
    private var v: Float = 0f
    private var paint = Paint()
    private var touching = false
    private var duet = false

    /**
     * We are bound to the synth service.
     */
    fun onConnected() {
        val scale = synthService!!.scale
        val octaves = synthService!!.octaves
        duet = synthService!!.isDuet

        nx = scale.size * octaves + 1
        ny = if (duet) nx else 0

        val colsX = IntArray(scale.size)

        for (i in scale.indices) {
            colsX[i] = noteColor(scale[i])
        }

        val scaleY = if (duet) scale else FloatArray(0)

        if (scaleY.isEmpty()) colsY = IntArray(0) else {
            colsY = IntArray(scaleY.size)
            for (i in scaleY.indices)
                colsY[i] = noteColor(scaleY[i])
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        u = event.x / sw
        v = 1.0f - event.y / sh

        return true
    }

    override fun onDraw(canvas: Canvas) {

        // 	Draw the user's finger.
        if (touching) {
            val cx = sw * (0.5f + (u * nx).toInt()) / nx
            val cy: Float = if (duet) sh * (0.5f + ((1 - v) * ny)) / ny else (1 - v) * sh
            canvas.drawCircle(cx, cy, sw / (2 * nx).toFloat(), paint)
        }
    }

    private fun noteColor(n: Float): Int {
        val a = 255
        val hsv = floatArrayOf(
            n / 12f * 300f,
            if (n == 0f) 0f else 1f,
            1f
        )
        return Color.HSVToColor(a, hsv)
    }
}