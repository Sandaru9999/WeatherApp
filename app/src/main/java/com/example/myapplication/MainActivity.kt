package com.example.myapplication



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val secondActbutton = findViewById<Button>(R.id.second_act_btn)
        secondActbutton.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        val thirdActbutton = findViewById<Button>(R.id.Third_act_btn)
        thirdActbutton.setOnClickListener {
            val intent = Intent(this, ThirdActivity::class.java)
            startActivity(intent)
        }

        // Move these lines inside the onCreate method
        val forthActbutton = findViewById<Button>(R.id.forth_act_btn)
        forthActbutton.setOnClickListener {
            val intent = Intent(this, forthActivity::class.java)
            startActivity(intent)
        }
    }
}
