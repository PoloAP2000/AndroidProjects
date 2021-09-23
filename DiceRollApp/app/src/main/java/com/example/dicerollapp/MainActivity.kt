package com.example.dicerollapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

class Dice(private val numSides: Int) {

    fun roll(): Int {
        return (1..numSides).random()
    }
}

var n = 1

class MainActivity : AppCompatActivity() {
    private var nbDices:Int = 4
    private var currentDice = -1
    private lateinit var nbDicesLbl:TextView
    private lateinit var dicesViewsTxt:Array<TextView>
    private lateinit var dicesViewsImg:Array<ImageView>
    private var isDiceStyleImg = true
    private var exList:List<Int> = listOf(1, 2, 3, 4, 5)
    private var exMutableList:MutableList<Int> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nbDicesLbl = findViewById(R.id.lblNbDices)
        dicesViewsTxt = Array[4, {
            if(it = 0)
        }]

        exMutableList.add(1)
        exMutableList.add(2)
        exMutableList.add(3)
        exMutableList.add(4)
        exMutableList.removeAt(2)
        exMutableList.sort()

        fun btnSwitchDiceStyleClicked(sender: View)
        {
            isDiceStyleImg = !isDiceStyleImg
            findViewById<View>(R.id.diceStyleText).isVisible = !isDiceStyleImg
            findViewById<View>(R.id.diceStyleImg).isVisible = !isDiceStyleImg
        }

        fun btnRollOneClicked(sender: View)
        {
            if(currentDice + 1 < nbDices)
            { currentDice++
            }
            else {
                currentDice = 0
                resetDices[]
            }
            rollDice(currentDice)
        }

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