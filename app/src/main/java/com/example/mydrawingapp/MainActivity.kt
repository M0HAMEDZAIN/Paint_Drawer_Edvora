package com.example.mydrawingapp


import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.Toast
import com.example.mydrawingapp.PaintView.Companion.defaultColor
import com.example.mydrawingapp.colorpallet.ColorList
import com.example.mydrawingapp.colorpallet.ColorObject
import com.example.mydrawingapp.colorpallet.ColorSpinnerAdapter
import com.example.mydrawingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object{
        var path = Path()
        val paintBrush = Paint()
    }

    var brushColor:String="#000000"

    lateinit var binding: ActivityMainBinding
    lateinit var selectedColor:ColorObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        loadColorSpinner()

        Toast.makeText(this,brushColor,Toast.LENGTH_SHORT).show()

        val pencilbtn = findViewById<ImageButton>(R.id.pencil_bottun)
        pencilbtn.setOnClickListener {
            Toast.makeText(this,"Pencil Clicked",Toast.LENGTH_SHORT).show()
            PaintView.currentShape=PaintView.Pencile
            path = Path()
        }

        val arrowbtn = findViewById<ImageButton>(R.id.arrow_bottun)
        arrowbtn.setOnClickListener {
            Toast.makeText(this,"Arrow Clicked",Toast.LENGTH_SHORT).show()
            PaintView.currentShape=PaintView.Arrow
            path = Path()
        }

        val rectanglebtn = findViewById<ImageButton>(R.id.rectangle_bottun)
        rectanglebtn.setOnClickListener {
            Toast.makeText(this,"Rectangle Clicked",Toast.LENGTH_SHORT).show()
            PaintView.currentShape=PaintView.Rectangle
            path = Path()
        }

        val circlebtn = findViewById<ImageButton>(R.id.circle_bottun)
        circlebtn.setOnClickListener {
            Toast.makeText(this,"Circle Clicked",Toast.LENGTH_SHORT).show()
            PaintView.currentShape=PaintView.Circle
            path = Path()
        }

    }

    // color pallet load function
    private fun loadColorSpinner()
    {
        selectedColor = ColorList().defaultColor
        binding.colorSpinner.apply {
            adapter = ColorSpinnerAdapter(applicationContext, ColorList().basicColors())
            setSelection(ColorList().colorPosition(selectedColor), false)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener
            {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long)
                {
                    selectedColor = ColorList().basicColors()[position]
                    brushColor=selectedColor.hexHash
                    paintBrush.color = Color.parseColor(brushColor)
                    currentColor(paintBrush.color)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }
        }
    }

    private fun currentColor(color:Int){
        defaultColor = color
        path = Path()
    }


}