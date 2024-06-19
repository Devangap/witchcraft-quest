package com.example.labexam3it22066466

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class MainActivity3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)
        val but11: Button = findViewById(R.id.but1)

        // Set OnClickListener for the button
        but11.setOnClickListener {
            // Start the SplashScreen activity
            startActivity(Intent(this@MainActivity3, SplashScreen::class.java))
        }
    }
}

