package com.example.birdcounter

import android.content.SharedPreferences.Editor
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadView()
        buttons()
    }

    private fun buttons() {
        red_button.setOnClickListener {
            counter += 1
            mainLayoutBackground.setBackgroundColor(Color.RED)
            val textView: TextView = findViewById(R.id.main_counter)
            textView.text = "$counter"
            saveView()
        }

        blue_button.setOnClickListener {
            counter += 1
            mainLayoutBackground.setBackgroundColor(Color.BLUE)
            val textView: TextView = findViewById(R.id.main_counter)
            textView.text = "$counter"
            saveView()
        }

        green_button.setOnClickListener {
            counter += 1
            mainLayoutBackground.setBackgroundColor(Color.GREEN)
            val textView: TextView = findViewById(R.id.main_counter)
            textView.text = "$counter"
            saveView()
        }

        yellow_button.setOnClickListener {
            counter += 1
            mainLayoutBackground.setBackgroundColor(Color.YELLOW)
            val textView: TextView = findViewById(R.id.main_counter)
            textView.text = "$counter"
            saveView()
        }

        reset_button.setOnClickListener {
            counter = 0
            mainLayoutBackground.setBackgroundColor(Color.WHITE)
            val textView: TextView = findViewById(R.id.main_counter)
            textView.text = counter.toString()
            saveView()
        }
    }

    private fun saveView() {
        val sharedPreferences =  getSharedPreferences("BIRDS_PREFERENCE", MODE_PRIVATE)
        val editor: Editor = sharedPreferences.edit()
        val background = mainLayoutBackground.background as ColorDrawable

        editor.putInt("layoutColor", background.color)
        editor.putInt("birdCounter", counter)
        editor.apply()
    }

    private fun loadView() {
        val sharedPreferences =  getSharedPreferences("BIRDS_PREFERENCE", MODE_PRIVATE)
        val layoutColor: Int = sharedPreferences.getInt("layoutColor", Color.WHITE)

        counter = sharedPreferences.getInt("birdCounter", 0)
        main_counter.text = counter.toString()
        mainLayoutBackground.setBackgroundColor(layoutColor)
    }
}
