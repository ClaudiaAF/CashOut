package com.example.cashout

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_add = findViewById<Button>(R.id.btn_add)
        //get sharedPreferences
        val sharedPref = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        // reference to TextView
        val label = findViewById<TextView>(R.id.tv_last)
        val numberLabel = findViewById<TextView>(R.id.tv_amount)

        // reference to store input
        val nameEditText = findViewById<EditText>(R.id.et_name)
        val amountField = findViewById<EditText>(R.id.et_amount)

        //set key values
        val store = sharedPref.getString("storeKey", "")
        var amount = sharedPref.getInt("amountKey", 0)

        //display sharedPref values
        label.text = "Last Amount Spent at $store"
        numberLabel.text = "$amount"

        //add values
        nameEditText.text.insert(0, "$store")
        amountField.text.insert(0, "$amount")

        btn_add.setOnClickListener {

            //get store value and add to sharedPrefs
            val editor = sharedPref.edit()
            editor.putString("storeKey", nameEditText.text.toString())

            //get amount value, sum up integers and add to sharedPrefs
            val sum = amount + Integer.parseInt(amountField.text.toString())
            editor.putInt("amountKey", sum)

            //display submitted store name
            label.text = "Last Amount Spent at " + nameEditText.text.toString()

            //display total amount
            numberLabel.text = sum.toString()
            editor.apply()
            amount = sum

            //clear input fields after button tap
            nameEditText.text.clear()
            amountField.text.clear()
        }

        
        val restartButton = findViewById<Button>(R.id.btn_refresh)

        restartButton.setOnClickListener {

            //reset values in sharedPrefs
            val editor = sharedPref.edit()
            editor.putString("storeKey", null)
            editor.putInt("amountKey", 0)

            //reset values and text in front-end
            label.text = "Last Amount Spent at No Store"
            numberLabel.text = "0"
            editor.apply()
            amount = 0

            //clear input fields after button tap
            nameEditText.text.clear()
            amountField.text.clear()

        }
    }
}

