package com.example.dicerollapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Dice(private val numSides: Int) {

    fun roll(): Int {
        return (1..numSides).random()
    }
}

var n = 1

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var rollButton: Button = findViewById(R.id.button)
        rollButton.setOnClickListener {
            rollDice()
        }

        var incrementbutton: Button = findViewById(R.id.button3)
        incrementbutton.setOnClickListener {
            n++ until 4
            val resulTextView:TextView = findViewById(R.id.textView)
            resulTextView.text = n.toString()
            if (n == 4)
                incrementbutton.isEnabled = false
        }

        var decrementbutton: Button = findViewById(R.id.button2)
        incrementbutton.setOnClickListener {
            n-- until 1
            val resulTextView:TextView = findViewById(R.id.textView)
            resulTextView.text = n.toString()
            if (n == 1)
                incrementbutton.isEnabled = false
        }
    }

    private fun rollDice() {
        val dice = Dice(6)
        val diceRoll = dice.roll()
        val resulTextView:TextView = findViewById(R.id.textView2)
        resulTextView.text = diceRoll.toString()
    }
}