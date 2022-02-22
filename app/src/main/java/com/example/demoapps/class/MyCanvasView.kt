package com.example.demoapps.`class`

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.os.Environment
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.example.demoapps.R
import java.io.*


private val STROKE_WIDTH=12f
class MyCanvasView(context: Context) : View(context){
    private var saveBtn: Button? =null
    private lateinit var extraCanvas: Canvas
    private lateinit var extraBitmap: Bitmap
    private val backGroundColor = ResourcesCompat.getColor(resources, R.color.colorBackground, null)
    private val drawColor= ResourcesCompat.getColor(resources,R.color.colorPaint,null)
    private var paint: Paint? =Paint().apply {
        color=drawColor
        this.isAntiAlias =true
        this.isDither = true
        style=Paint.Style.STROKE
        strokeJoin=Paint.Join.ROUND
        strokeCap=Paint.Cap.ROUND
        strokeWidth =STROKE_WIDTH
    }
    private var path=Path()
    private var motionTouchEventX = 0f
    private var motionTouchEventY = 0f
    private var currentX = 0f
    private var currentY = 0f
    private val touchTolerance = ViewConfiguration.get(context).scaledTouchSlop
//    private lateinit var frame: Rect
    private val drawing = Path()
    private val curPath = Path()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        extraBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        extraCanvas = Canvas(extraBitmap)
        extraCanvas.drawColor(backGroundColor)

       /* val inset = 40
        frame = Rect(inset, inset, width - inset, height - inset)*/
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas!!.drawBitmap(extraBitmap,0f,0f,null)

//        canvas.drawRect(frame, paint!!)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        motionTouchEventX = event!!.x
        motionTouchEventY = event!!.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> touchStart()
            MotionEvent.ACTION_MOVE -> touchMove()
            MotionEvent.ACTION_UP -> touchUp()
        }
        return true
    }
    private fun touchStart() {
        path.reset()
        path.moveTo(motionTouchEventX, motionTouchEventY)
        currentX = motionTouchEventX
        currentY = motionTouchEventY

    }
    private fun touchMove() {
        val dx = Math.abs(motionTouchEventX - currentX)
        val dy = Math.abs(motionTouchEventY - currentY)
        if (dx >= touchTolerance || dy >= touchTolerance) {

            path.quadTo(currentX, currentY, (motionTouchEventX + currentX) / 2, (motionTouchEventY + currentY) / 2)
            currentX = motionTouchEventX
            currentY = motionTouchEventY

            extraCanvas.drawPath(path, paint!!)
        }
        invalidate()
    }
    private fun touchUp() {
        path.reset()
    }

    fun setClick(): Bitmap {
        this.setDrawingCacheEnabled(true)
        this.buildDrawingCache()
        this.setDrawingCacheEnabled(false)
        extraCanvas.setBitmap(extraBitmap)
           var outputStream: OutputStream? =null
           val filepath: File = Environment.getExternalStorageDirectory()
           val dir = File(filepath.getAbsoluteFile().toString() + "/SaveImage")
           dir.mkdir()
           val fileName = System.currentTimeMillis().toString() + ".jpg"
           val file = File(dir, fileName)
           try {
               outputStream = FileOutputStream(file)
               //Bitmap image
               extraBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
               //flush or close
               outputStream.flush()
               outputStream.close()
               Toast.makeText(context, "Save in Gallery", Toast.LENGTH_LONG).show()
               Log.d("SaveData", "Data")
           } catch (e: FileNotFoundException) {
               e.printStackTrace()
           } catch (e: IOException) {
               e.printStackTrace()
       }
        return extraBitmap

    }

    fun setImage(){
        extraCanvas.setBitmap(extraBitmap)
        extraBitmap
    }

    fun getBitmap(): Bitmap {
        extraCanvas.setBitmap(extraBitmap)
        return extraBitmap
    }
}