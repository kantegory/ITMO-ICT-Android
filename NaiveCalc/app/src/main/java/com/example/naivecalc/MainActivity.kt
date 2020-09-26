package com.example.naivecalc

import android.R.attr.label
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
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
        val numValues = "0123456789"
        val natValues = "123456789"
        val zeroValue = "0"
        val opValues = "×/-+"
        val pointValue = "."

        // logic is there
        // check if field is not empty for adding only numbers or point
        if (resultField.text.isEmpty()) {
             if (btnVal in numValues) {
                 resString += btnVal.toString()
             }
        } else {
            // if last char is not operation, we can add new char
            if (resultField.text.takeLast(1) in numValues ||
                ((resultField.text.takeLast(1) in pointValue) && (btnVal.toString() in numValues))
            ) {
                resString += btnVal.toString()
            }

            // if last char is an operation, we can add only num
            else if (resultField.text.takeLast(1) in opValues && btnVal.toString() in numValues) {
                resString += btnVal.toString()
            }

            // if last char is zero, we can't add a new one zero
            else if (resultField.text.takeLast(1) in zeroValue && ((btnVal.toString() in natValues)
                        || (btnVal.toString() in opValues) || (btnVal.toString() in pointValue))
            ) {
                resString += btnVal.toString()
            }
        }
        // update result field
        resultField.text = resString
    }

    fun showRes(view: View) {
        // get result field
        val resultField = findViewById<TextView>(R.id.result)
        var tempString = ""
        val numArray: MutableList<Double> = ArrayList()
        val opArray: MutableList<String> = ArrayList()

        // define numValues and opValues
        val numValues = ".0123456789"
        val opValues = "×/-+"

        // if last char is operation we shouldn't calc
        if (resultField.text.takeLast(1) in opValues) {
            return
        }

        resultField.text.forEach {
            run {
                // if it's number we should save it to tempString
                if (it in numValues) {
                    tempString += it
                }
                else if (it in opValues) {
                    // add curr tempString double number to numArray
                    numArray.add(tempString.toDouble())

                    // clear tempString
                    tempString = ""

                    // add opValue to opArray
                    opArray.add(it.toString())
                }
            }
        }

        // get the last number from resultField
        var lastNum = resultField.text.split("+").takeLast(1)[0]
        lastNum = lastNum.split("-").takeLast(1)[0]
        lastNum = lastNum.split("/").takeLast(1)[0]
        lastNum = lastNum.split("×").takeLast(1)[0]

        numArray.add(lastNum.toDouble())

        // update result field
        resultField.text = calc(numArray, opArray)
    }

     private fun calc(numArray: MutableList<Double>, opArray: MutableList<String>): String {
         var result = 0.0
         var tempNum = 0.0

         numArray.forEachIndexed { Index, Num ->
             run {
                 if (Index < 1) {
                     result = Num
                 } else {
                     when {
                         opArray[Index - 1] == "+" -> {
                             tempNum = Num
                             result += tempNum
                         }
                         opArray[Index - 1] == "-" -> {
                             tempNum = Num
                             result -= tempNum
                         }
                         opArray[Index - 1] == "×" -> {
                             tempNum = Num
                             result *= tempNum
                         }
                         opArray[Index - 1] == "/" -> {
                             tempNum = Num
                             result /= tempNum
                         }
                     }
                 }
             }
         }

         return result.toString()
     }

    fun copyResToClipboard(view: View) {
        // init clipboard manager
        val clipboard =
            getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        // get clip data
        val clip = ClipData.newPlainText(label.toString(), findViewById<TextView>(view.id).text.toString())

        // put it to clipboard
        clipboard.setPrimaryClip(clip);

        // show toast
        Toast.makeText(applicationContext,"Результат успешно скопирован!", Toast.LENGTH_SHORT).show()
    }
}
