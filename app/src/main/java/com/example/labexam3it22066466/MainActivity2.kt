package com.example.labexam3it22066466

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import java.util.Timer
import java.util.TimerTask
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {
    private lateinit var gameView: WitchView
    private val handler = Handler()
    private val interval = 30L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        gameView = WitchView(this)
        setContentView(gameView)

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                handler.post {
                    gameView.invalidate()
                }
            }
        }, 0, interval)
    }
}