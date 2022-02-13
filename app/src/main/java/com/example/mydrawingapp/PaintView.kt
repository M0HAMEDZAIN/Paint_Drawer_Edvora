package com.example.mydrawingapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mydrawingapp.MainActivity.Companion.paintBrush
import com.example.mydrawingapp.MainActivity.Companion.path
import kotlin.math.pow
import kotlin.math.sqrt

class PaintView : View {

    var params: ViewGroup.LayoutParams? = null

    private var isDrawing = false
    private var StartX: Float = 0f
    private var StartY: Float = 0f
    var getx: Float = 0f
    var gety: Float = 0f
    private var mCanvas: Canvas? = null


    constructor(context: Context) : this(context, null) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }


    companion object {
        var pathList = ArrayList<Path>()
        var colorList = ArrayList<Int>()
        var defaultColor = Color.BLACK
        var currentShape = 0
        var Pencile = 1
        var Arrow = 2
        var Rectangle = 3
        var Circle = 4
    }

    private fun init() {
        paintBrush.isAntiAlias = true
        paintBrush.color = defaultColor
        paintBrush.style = Paint.Style.STROKE
        paintBrush.strokeJoin = Paint.Join.ROUND
        paintBrush.strokeWidth = 8f
        params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    fun reset() {
        path = Path()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        getx = event.x
        gety = event.y
        when (currentShape) {
            Arrow -> onTouchEventArrow(event)
            Pencile -> onTouchEventPencil(event)
            Rectangle -> onTouchEventRectangle(event)
            Circle -> onTouchEventCircle(event)
        }
        return true
    }

    private fun onTouchEventArrow(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isDrawing = true
                StartX = getx
                StartY = gety
                invalidate()
                return true
            }
            MotionEvent.ACTION_MOVE -> invalidate()
            MotionEvent.ACTION_UP -> {
                isDrawing = false
                invalidate()
                return true
            }
        }
        postInvalidate()
        return false
    }


    private fun onTouchEventPencil(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(getx, gety)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(getx, gety)
                pathList.add(path)
                colorList.add(defaultColor)

            }
            else -> return false
        }
        postInvalidate()
        return false
    }

    private fun onTouchEventRectangle(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isDrawing = true
                StartX = getx
                StartY = gety
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> invalidate()
            MotionEvent.ACTION_UP -> {
                isDrawing = false
                invalidate()
            }
        }
        postInvalidate()
    }

    private fun onTouchEventCircle(event: MotionEvent) {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isDrawing = true
                StartX = getx
                StartY = gety
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> invalidate()
            MotionEvent.ACTION_UP -> {
                isDrawing = false
                invalidate()
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        onDrawPencile(canvas)
            when (currentShape) {
                Arrow -> onDrawArrow(canvas)
                Rectangle -> onDrawRectangle(canvas)
                Circle -> onDrawCircle(canvas)
            }
        }

    private fun onDrawPencile(canvas: Canvas) {
        for (i in pathList.indices) {
            paintBrush.setColor(colorList[i])
            canvas.drawPath(pathList[i], paintBrush)
            invalidate()
        }
    }

    private fun calculateRadius(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        return sqrt(
            (x1 - x2).toDouble().pow(2.0) +
                    (y1 - y2).toDouble().pow(2.0)
        ).toFloat()
    }

    private fun onDrawCircle(canvas: Canvas) {
        canvas.drawCircle(StartX, StartY, calculateRadius(StartX, StartY, getx, gety), paintBrush)
        Toast.makeText(context,"Circle ----- ",Toast.LENGTH_LONG)
    }

    private fun onDrawRectangle(canvas: Canvas?) {
        val right = if (StartX > getx) StartX else getx
        val left = if (StartX > getx) getx else StartX
        val bottom = if (StartY > gety) StartY else gety
        val top = if (StartY > gety) gety else StartY

        canvas!!.drawRect(left, top, right, bottom, paintBrush)
    }

    private fun onDrawArrow(canvas: Canvas?) {
        canvas!!.drawLine(StartX, StartY, getx, gety, paintBrush)
        invalidate()
    }
}