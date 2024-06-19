package com.example.labexam3it22066466

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.BitmapFactory
import android.view.View
import android.graphics.Paint
import android.graphics.Color
import android.graphics.Typeface
import android.view.MotionEvent
import android.content.Intent

class WitchView(context: Context) : View(context) {
    private var witch: Array<Bitmap?> = arrayOfNulls(2);
    private var potion: Bitmap;
    private var hat: Bitmap;
    private var bomb: Bitmap;
    private var bgimg:Bitmap;
    private var scorePaint: Paint = Paint()
    private var life: Array<Bitmap?> = arrayOfNulls(2)
    private var witchx = 10
    private var witchY: Int = 0 // You need to specify the type for uninitialized variables
    private var witchspeed: Int = 0 // Assuming you want to specify the type explicitly
    private var canvasWidth: Int = 0 // Corrected typo: canvaswidth to canvasWidth
    private var canvasHeight: Int = 0 //
    private var touch :Boolean = false
    private var potionx: Int = 0
    private var potionY: Int = 0
    private var potionSpeed: Int = 16
    private val yellowPaint: Paint = Paint()
    private var score:Int = 0
    private var lifecounterofwitch = 0



    private var greenX: Int = 0
    private var greenY: Int = 0
    private var greenSpeed: Double = 10.0
    private val greenPaint: Paint = Paint()


    private var redX: Int = 0
    private var redY: Int = 0
    private var redSpeed: Int= 30
    private val redPaint: Paint = Paint()

    init {
        witch[0] = BitmapFactory.decodeResource(resources, R.drawable.witch)
        witch[1] = BitmapFactory.decodeResource(resources, R.drawable.witch2)
        bgimg =BitmapFactory.decodeResource(resources, R.drawable.bg2)
        life[0] = BitmapFactory.decodeResource(resources, R.drawable.hearts)
        life[1] = BitmapFactory.decodeResource(resources, R.drawable.heart_grey)
        potion = BitmapFactory.decodeResource(resources, R.drawable.magicpotion)
        hat = BitmapFactory.decodeResource(resources, R.drawable.witchhat)
        bomb = BitmapFactory.decodeResource(resources, R.drawable.bomb)

        scorePaint.color = Color.WHITE
        scorePaint.textSize = 70f
        scorePaint.typeface = Typeface.DEFAULT_BOLD
        scorePaint.isAntiAlias = true
        witchY = 550;
        score = 0 ;
        lifecounterofwitch = 3;
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawBitmap(bgimg, 0f, 0f, null)

        canvas.drawBitmap(potion, potionx.toFloat(), potionY.toFloat(), null)

        canvas.drawBitmap(hat, greenX.toFloat(), greenY.toFloat(), null)
        canvas.drawBitmap(bomb, redX.toFloat(), redY.toFloat(), null)



        canvasWidth = canvas.width
        canvasHeight = canvas.height
        val minwitchx = witch[0]?.height ?: 0 // Handle potential null witch[0]
        val maxwitchx = canvasHeight - (witch[0]?.height ?: 0) * 3

        witchY += witchspeed

        if (witchY < minwitchx)
            witchY = minwitchx

        if (witchY > maxwitchx)
            witchY = maxwitchx
        witchspeed += 2

        if (touch) {
            witch[1]?.let { bitmap ->
                canvas.drawBitmap(bitmap, witchx.toFloat(), witchY.toFloat(), null)
            }
            touch = false
        } else {
            witch[0]?.let { bitmap ->
                canvas.drawBitmap(bitmap, witchx.toFloat(), witchY.toFloat(), null)
            }
        }

        potionx -= potionSpeed
        if (hitBallChecker(potionx, potionY)) {
            score += 10
            potionx = -100
        }
        if (potionx < 0) {
            potionx = canvasWidth + 21
            potionY = (Math.random() * (maxwitchx - minwitchx) + minwitchx).toInt()
        }

        greenX -= greenSpeed.toInt()
        if (hitBallChecker(greenX, greenY)) {
            score += 20
            greenX = -100
        }
        if (greenX < 0) {
            greenX = canvasWidth + 21
            greenY = (Math.random() * (maxwitchx - minwitchx) + minwitchx).toInt()
        }


        redX -= redSpeed.toInt()
        if (hitBallChecker(redX, redY)) {

            redX = -100
            lifecounterofwitch --

// Check if the life counter has reached zero
            if (lifecounterofwitch == 0) {
                val gameOverIntent = Intent(getContext(), GameOver::class.java)
                gameOverIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                gameOverIntent.putExtra("score", score.toString())
                getContext().startActivity(gameOverIntent)
            }
        }
        if (redX < 0) {
            redX = canvasWidth + 21
            redY = (Math.random() * (maxwitchx - minwitchx) + minwitchx).toInt()
        }

        canvas.drawText("Score : "+ score, 40f, 160f, scorePaint)
        for (i in 0 until 3) {
            val t = (600 + (life[0]?.width ?: 0) * 1.5 * i).toInt()
            val y = 90
            if (i < lifecounterofwitch) {
                life[0]?.let {
                    canvas.drawBitmap(it, t.toFloat(), y.toFloat(), null)
                }
            } else {
                life[1]?.let {
                    canvas.drawBitmap(it, t.toFloat(), y.toFloat(), null)
                }
            }
        }



    }
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            touch = true
        }
        witchspeed = -22
        return true
    }
    fun hitBallChecker(x: Int, y: Int): Boolean {
        // Ensure witch[0] is not null before accessing its properties
        val witchWidth = witch[0]?.getWidth() ?: 0
        val witchHeight = witch[0]?.getHeight() ?: 0

        val witchright = witchx + witchWidth
        val witchbottom = witchY + witchHeight
        return (witchx < x && x < witchright && witchY < y && y < witchbottom)
    }


}