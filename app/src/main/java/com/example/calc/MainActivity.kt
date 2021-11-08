package com.example.calc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private var textInput: TextView? = null
    var lastNum = false
    var hasDot = false
    var byZero = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textInput = findViewById(R.id.textView)
    }

    fun onDigit(view: View){
        // Toast.makeText(this, "Button clicked", Toast.LENGTH_SHORT).show()
        if (!byZero){
            textInput?.append((view as Button).text)
            lastNum = true
        }
    }

    fun onBack(view: View){
        if (!byZero){
            textInput?.text = textInput?.text?.dropLast(1)
            if (textInput?.length()!! > 0){
                lastNum = true
            }
        }
    }

    fun onClear(view: View){
        textInput?.text = ""
        byZero = false
    }

    fun onOperator(view: View){
        if (!byZero){
            textInput?.text?.let {
                if (lastNum && !isOperatorAdded(it.toString())){
                    textInput?.append((view as Button).text)
                    lastNum = false
                    hasDot = false
                }
            }
        }
    }

    fun onFloat(view: View){
        if (!byZero)
        if (lastNum && !hasDot){
            textInput?.append((view as Button).text)
            lastNum = false
            hasDot = true
        }
    }

    fun isEqual(view: View){
        if (!byZero){
            var result = textInput?.text.toString()
            var prefix = ""

            try {
                if(result.startsWith("-")){
                    prefix = "-"
                    result = result.substring(1)
                }
                if (result.contains("-")){
                    val splitValue = result.split("-")
                    var first = splitValue[0]
                    var second = splitValue[1]

                    if (prefix.isNotEmpty()){
                        first = prefix + first
                    }

                    var total = (first.toDouble() - second.toDouble()).toString()
                    textInput?.text = doubleToInt(total)
                }else if (result.contains("+")){
                    val splitValue = result.split("+")
                    var first = splitValue[0]
                    var second = splitValue[1]

                    if (prefix.isNotEmpty()){
                        first = prefix + first
                    }

                    var total = (first.toDouble() + second.toDouble()).toString()
                    textInput?.text = doubleToInt(total)
                }else if (result.contains("*")){
                    val splitValue = result.split("*")
                    var first = splitValue[0]
                    var second = splitValue[1]

                    if (prefix.isNotEmpty()){
                        first = prefix + first
                    }

                    var total = (first.toDouble() * second.toDouble()).toString()
                    textInput?.text = doubleToInt(total)
                }else if (result.contains("/")){
                    val splitValue = result.split("/")
                    var first = splitValue[0]
                    var second = splitValue[1]

                    if (prefix.isNotEmpty()){
                        first = prefix + first
                    }
                    // TODO: by zero
                    if (second.toInt() == 0){
                        textInput?.text = "Cannot divide by zero"
                        byZero = true
                    }else{
                        var total = (first.toDouble() / second.toDouble()).toString()
                        textInput?.text = doubleToInt(total)
                    }

                }else if (result.contains("%")){
                    val splitValue = result.split("%")
                    var first = splitValue[0]
                    var second = splitValue[1]

                    if (prefix.isNotEmpty()){
                        first = prefix + first
                    }
                    // TODO: by zero
                    if (second.toInt() == 0){
                        textInput?.text = "Cannot % by zero"
                        byZero = true
                    }else{
                        var total = (first.toDouble() % second.toDouble()).toString()
                        textInput?.text = doubleToInt(total)
                    }

                }
            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }


    }

    private fun doubleToInt(value: String): String {

        if (value.endsWith(".0")) {
            return value.dropLast(2)
        }
        return value
    }

    private fun isOperatorAdded(value: String) : Boolean{
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("%")
                    || value.contains("/")
                    || value.contains("*")
                    || value.contains("-")
                    || value.contains("+")
        }
    }
}