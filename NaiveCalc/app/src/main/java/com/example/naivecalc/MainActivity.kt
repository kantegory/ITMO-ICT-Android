package com.example.naivecalc

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // add scrolling to result field
        findViewById<TextView>(R.id.result).movementMethod = ScrollingMovementMethod()
    }

    @SuppressLint("SetTextI18n")
    fun addOpToResField(view: View) {
        // get result field
        val resultField = findViewById<TextView>(R.id.result)
        val btnVal = findViewById<Button>(view.id).text
        var resString = resultField.text.toString()

        // define numValues and opValues
        val numValues = "123456789"
        val zeroValue = "0"
        val opValues = ".×/-+"

        // logic is there
        // check if field is not empty for adding only numbers or point
        if (resultField.text.isEmpty()) {
             if (btnVal in numValues || btnVal in zeroValue) {
                 resString += btnVal.toString()
             }
        }
        // if last char is not operation, we can add new char
        else if (resultField.text.takeLast(1) in numValues) {
            resString += btnVal.toString()
        }
        // if last char is an operation, we can add only num
        else if (resultField.text.takeLast(1) in opValues && btnVal.toString() in numValues) {
            resString += btnVal.toString()
        }
        // if last char is zero, we can't add a new one zero
        else if (resultField.text.takeLast(1) in zeroValue && btnVal.toString() in numValues) {
            resString += btnVal.toString()
        }

        // update result field
        resultField.text = resString
    }

    fun showRes(view: View) {}
}
