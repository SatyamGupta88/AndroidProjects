package com.example.myc

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import android.widget.Button
import com.example.myc.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var stateerror = false
    var lastdot = false
    var numericdata = false
    //it evaluate the expression entred bu usr in input field
    private lateinit var expression: Expression
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onClickallclear(view: View) {
        binding.tvinput.text=""
        binding.tvoutput.text=""
        stateerror=false
        numericdata=false
        lastdot=false
        binding.tvoutput.visibility = View.GONE
    }
    fun onClickequal(view: View) {
        //compute expression
        onEqual()
        binding.tvinput.text = binding.tvoutput.text.toString().drop(1)
    }
    fun onClickdigit(view: View) {
            if(stateerror)
            {
                binding.tvinput.text = (view as Button).text
                stateerror = false
            }
        else
            {
                binding.tvinput.append((view as Button).text)
            }
        numericdata=true
        onEqual()
    }
    fun onClickoperator(view: View) {

        if(!stateerror && numericdata)
        {
            binding.tvinput.append((view as Button).text)
            lastdot = false
            numericdata=false
            onEqual()
        }
    }
     fun onClickback(view: View) {

        binding.tvinput.text = binding.tvinput.text.toString().dropLast(1)
        try{
            val last = binding.tvinput.text.toString().last()
            if(last.isDigit())
            {
                onEqual()
            }
        }
        catch(e:Exception){
            binding.tvoutput.text = ""
            binding.tvoutput.visibility = View.GONE
            Log.e("last char error",e.toString())
        }
    }
    fun onEqual(){
        if(numericdata && !stateerror)
        {
            val txt = binding.tvinput.text.toString()

            expression = ExpressionBuilder(txt).build()

            try {
                val result = expression.evaluate()
                //make oput tv visible
                binding.tvoutput.visibility = View.VISIBLE
                //show output
                binding.tvoutput.text = "="+result.toString()
            }
            catch(e:ArithmeticException){
                Log.e("eveluate error",e.toString())
                binding.tvoutput.text="Error"
                stateerror = true
                numericdata=false
            }
        }
    }
}