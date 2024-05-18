package com.example.mycalculator

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    var flagdeci: Boolean=false
    var flagnum: Boolean=false
    var currsize:Float=60F
    var decreasetimes=8
    var flaganswered: Boolean=false
    var flagerror:Boolean=false
    fun addToS(view:View)
    {
        if(flaganswered)
        {
            clear(view)
            flaganswered=false
        }
        if(findViewById<TextView>(R.id.questionpanel).text.length%10==0 && decreasetimes>=0)
        {
            findViewById<TextView>(R.id.questionpanel).textSize=currsize-5
            currsize-=5
            decreasetimes--
        }
           findViewById<TextView>(R.id.questionpanel).append((view as Button).text)
           flagnum=true
            val q: String = findViewById<TextView>(R.id.questionpanel).text.toString()
            val ans: Double = calculate(q)
            if(flagerror) {
                Toast.makeText(this, "Cant Divide By Zero", Toast.LENGTH_SHORT).show()
                findViewById<TextView>(R.id.answerpanel).text=""
                flagerror=false
            }
            else
            findViewById<TextView>(R.id.answerpanel).text = removeTrailingZeroes(ans)
    }

    fun decimal(view:View)
    {
        if(!flagdeci)
        {
            if(findViewById<TextView>(R.id.questionpanel).text.isEmpty())
            {
                findViewById<TextView>(R.id.questionpanel).append("0.")
                flagdeci=true
                flagnum=false
            }
            else{
                findViewById<TextView>(R.id.questionpanel).append(".")
                flagdeci=true
                flagnum=false
            }
        }
        else{
            Toast.makeText(this, "Cant use multiple decimals", Toast.LENGTH_SHORT).show()
        }
    }
    fun back(view:View)
    {
        if(flaganswered)
        {
            flagdeci=false;
            currsize=60F
            findViewById<TextView>(R.id.questionpanel).textSize=50F
            findViewById<TextView>(R.id.questionpanel).setTextColor(Color.WHITE)
            findViewById<TextView>(R.id.answerpanel).setTextColor(Color.GRAY)
            findViewById<TextView>(R.id.answerpanel).textSize = 30F
            flaganswered=false
        }
        var text : String = findViewById<TextView>(R.id.questionpanel).text.toString()
        if (text.isNotEmpty()) {
            findViewById<TextView>(R.id.questionpanel).text= text.substring(0, text.length - 1)
        }
        text = findViewById<TextView>(R.id.questionpanel).text.toString()
        if(text.endsWith("-")||text.endsWith("+")||text.endsWith("×")||text.endsWith("÷"))
            flagnum=false
        else {
            flagnum = true
            val q:String=findViewById<TextView>(R.id.questionpanel).text.toString()
            val ans:Double=calculate(q)
            findViewById<TextView>(R.id.answerpanel).text=removeTrailingZeroes(ans)
        }
    }

    fun addTOSminus(view:View)
    {
        val text : String = findViewById<TextView>(R.id.questionpanel).text.toString()
        if (text.isNotEmpty()) {
            if(text[(text.length)-1]=='-')
                Toast.makeText(this,"Two Consecutive Operators Not Allowed",Toast.LENGTH_SHORT).show()
            else{
                if(findViewById<TextView>(R.id.questionpanel).text.length%10==0 && decreasetimes>=0)
                {
                    findViewById<TextView>(R.id.questionpanel).textSize=currsize-5
                    currsize-=5
                    decreasetimes--
                }
                findViewById<TextView>(R.id.questionpanel).append((view as Button).text)
                flagnum = false
                flagdeci = false
            }
        }
        else
        {
            if(findViewById<TextView>(R.id.questionpanel).text.length%10==0 && decreasetimes>=0)
            {
                findViewById<TextView>(R.id.questionpanel).textSize=currsize-5
                currsize-=5
                decreasetimes--
            }
            findViewById<TextView>(R.id.questionpanel).append((view as Button).text)
            flagnum = false
            flagdeci = false
        }
    }
    fun addTOSsymbol(view:View)
    {
        if(flaganswered)
        {
            flagdeci=false;
            currsize=60F
            findViewById<TextView>(R.id.questionpanel).textSize=50F
            findViewById<TextView>(R.id.questionpanel).setTextColor(Color.WHITE)
            findViewById<TextView>(R.id.answerpanel).setTextColor(Color.GRAY)
            findViewById<TextView>(R.id.answerpanel).textSize = 30F
            flaganswered=false
        }
            if (flagnum) {
                if(findViewById<TextView>(R.id.questionpanel).text.length%10==0 && decreasetimes>=0)
                {
                    findViewById<TextView>(R.id.questionpanel).textSize=currsize-5
                    currsize-=5
                    decreasetimes--
                }
                findViewById<TextView>(R.id.questionpanel).append((view as Button).text)
                flagnum = false
                flagdeci = false
            }
        else{
            Toast.makeText(this,"Two Consecutive Operators Not Allowed",Toast.LENGTH_SHORT).show()
        }
    }

    fun removeTrailingZeroes(number: Double): String {
        return BigDecimal(number.toString()).stripTrailingZeros().toPlainString()
    }
    fun equals(view: View)
    {
        if(flagnum)
        {
            val q:String=findViewById<TextView>(R.id.questionpanel).text.toString()
            val ans:Double=calculate(q)
            val intans:Int=ans.toInt()
            val text:TextView = findViewById<TextView>(R.id.answerpanel)
            val text2:TextView = findViewById<TextView>(R.id.questionpanel)
            text.setTextColor(Color.WHITE)
            text.textSize = 45F
            text2.textSize = 26F
            text2.setTextColor(Color.GRAY)
            findViewById<TextView>(R.id.answerpanel).text=removeTrailingZeroes(ans)
            flaganswered=true
        }
    }
    fun clear(view:View)
    {
        flagdeci=false;
        currsize=60F
        findViewById<TextView>(R.id.questionpanel).text=""
        findViewById<TextView>(R.id.answerpanel).text=""
        findViewById<TextView>(R.id.questionpanel).textSize=60F
        findViewById<TextView>(R.id.questionpanel).setTextColor(Color.WHITE)
        findViewById<TextView>(R.id.answerpanel).setTextColor(Color.GRAY)
        findViewById<TextView>(R.id.answerpanel).textSize = 30F
    }

    fun calculate(q:String):Double
    {
        var prefix=""
        var newq=q
        if(q.startsWith("-"))
        {
            prefix="-"
            newq=q.substring(1)
        }
        if(q.contains('+'))
        {
            val splitVal=newq.split("+",limit=2)
            var one=calculate(splitVal[0])
            var two=calculate(splitVal[1])
            if(!prefix.isEmpty())
            {
                one=one*(-1.0);
                prefix=""
            }
            return one+two
        }
        else if(newq.contains('-'))
        {
            val splitVal=newq.split("-",limit=2)
            var one=calculate(splitVal[0])
            var two=calculate(splitVal[1])
            if(!prefix.isEmpty())
            {
                one=one*(-1.0);
                prefix=""
            }
            return one-two
        }
        else if(q.contains('×'))
        {
            val splitVal=newq.split("×",limit=2)
            var one=calculate(splitVal[0])
            var two=calculate(splitVal[1])
            if(!prefix.isEmpty())
            {
                one=one*(-1.0);
                prefix=""
            }
            return one*two
        }
        else if(q.contains('÷'))
        {
            val splitVal=q.split("÷", limit = 2)
            var one=calculate(splitVal[0])
            var two=calculate(splitVal[1])
            if(two==0.0)
            {
                flagerror=true
                return 1.0
            }
            else
                if(!prefix.isEmpty())
                {
                    one=one*(-1.0)
                    prefix=""
                }
                return one/two
        }
        else{
            return q.toDouble()
        }
        return 0.0
    }
}