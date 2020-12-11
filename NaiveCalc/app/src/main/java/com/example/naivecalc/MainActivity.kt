package com.example.naivecalc

import android.R.attr.label
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
import kotlinx.android.synthetic.main.activity_main.*
import org.mariuszgromada.math.mxparser.Expression


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // add scrolling to result field
        findViewById<TextView>(R.id.result).movementMethod = ScrollingMovementMethod()
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putString("result", findViewById<TextView>(R.id.result).text.toString())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        findViewById<TextView>(R.id.result).text = savedInstanceState.getString("result")
    }

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
        // check if field is not empty for adding only numbers or point or left bracket
        if (resultField.text.isEmpty()) {
             if (btnVal in numValues || btnVal in "(") {
                 resString += btnVal.toString()
             }
        } else {
            // if last char is not operation, we can add new char
            if ((resultField.text.takeLast(1) in numValues && btnVal !in "(") ||
                ((resultField.text.takeLast(1) in pointValue) &&
                        (btnVal in numValues))
            ) {
                resString += btnVal.toString()
            }

            // if last char is an operation, we can add only num or left bracket
            else if (resultField.text.takeLast(1) in opValues &&
                (btnVal in numValues || btnVal in "(")) {
                resString += btnVal.toString()
            }

            // if last char is zero, we can't add a new one zero
            else if (resultField.text.takeLast(1) in zeroValue && ((btnVal in natValues)
                        || (btnVal in opValues) || (btnVal in pointValue))
            ) {
                resString += btnVal.toString()
            }

            // if last char is left bracket, we can add only num
            else if (resultField.text.takeLast(1) in "(" && btnVal in numValues) {
                resString += btnVal.toString()
            }

            // if last char is right bracket, we can add only operation
            else if (resultField.text.takeLast(1) in ")" && btnVal in opValues) {
                resString += btnVal.toString()
            }

            // if we have an even number of left brackets, we can add right bracket
            else if (btnVal in ")" &&
                (countBrackets(resultField.text.toString(), "(") % 2 != 0 &&
                    countBrackets(resultField.text.toString(), ")") + 1 <
                    countBrackets(resultField.text.toString(), "(")
                ) && resultField.text.takeLast(1) !in "(") {
                resString += btnVal.toString()
            }
        }
        // update result field
        resultField.text = resString
    }

    private fun prepareExpression(expression: String) : String {
        return expression.replace("×", "*")
    }

    fun showRes(view: View) {
        // define operation values
        val opValues = "×/-+"
        val pointValue = "."

        // get result field
        val resultField = findViewById<TextView>(R.id.result)

        // count left brackets
        val leftBrackets = countBrackets(resultField.text.toString(), "(")

        // count right brackets
        val rightBrackets = countBrackets(resultField.text.toString(), ")")

        // prepare expression
        val expression = prepareExpression(resultField.text.toString())

        // parse expression
        val result = Expression(expression)

        // if count of left brackets lower than count of right brackets,
        // we shouldn't show the expression
        if (leftBrackets == rightBrackets &&
            resultField.text.takeLast(1) !in opValues &&
                resultField.text.takeLast(1) !in pointValue) {
            // calculate expression and put result to result field
            resultField.text = result.calculate().toString()
        }
    }

    fun clearResField(view: View) {
        // get result field, then clear
        findViewById<TextView>(R.id.result).text = ""
    }

    private fun countBrackets(s: String, ch: String) : Int {
        return s.filter { it in ch }.count()
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
