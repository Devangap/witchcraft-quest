package com.example.labexam3it22066466

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.EditText
import android.widget.Button
import android.widget.TextView
import android.content.SharedPreferences
import android.content.Context

class GameOver : AppCompatActivity() {
    private lateinit var playagain: Button
    private lateinit var getscore: TextView
    private lateinit var highscoreTextView: TextView
    private var score: String = ""
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game_over)
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        playagain = findViewById(R.id.playagain)
        getscore = findViewById(R.id.getscore)
        highscoreTextView = findViewById(R.id.highscore)

        // Retrieve the score from the intent extras
        score = intent.getStringExtra("score") ?: ""



        playagain.setOnClickListener {
            val intent = Intent(this, SplashScreen::class.java)
            startActivity(intent)
        }

        // Set the score to the TextView
        getscore.text = "$score"

        // Retrieve the stored high score
        val highScore = sharedPreferences.getInt("highScore", 0)

        // Update the high score if the current score is higher
        val currentScore = score.toIntOrNull() ?: 0
        if (currentScore > highScore) {
            with(sharedPreferences.edit()) {
                putInt("highScore", currentScore)
                apply()
            }
            // Display the new high score
            highscoreTextView.text = "High Score : $currentScore"
        } else {
            // Display the stored high score
            highscoreTextView.text = "High Score : $highScore"
        }
    }
}

