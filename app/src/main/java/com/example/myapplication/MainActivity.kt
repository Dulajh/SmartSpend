package com.example.myapplication

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.foodButton.setOnClickListener {
            val intent = Intent(this, ActivityFood::class.java)
            startActivity(intent)
        }
        binding.AccButton.setOnClickListener {
            val intent = Intent(this, ActivityAcomn::class.java)
            startActivity(intent)
        }
        binding.StetionButton.setOnClickListener {
            val intent = Intent(this, ActivitySte::class.java)
            startActivity(intent)
        }
        binding.OtherButton.setOnClickListener {
            val intent = Intent(this, ActivityOther::class.java)
            startActivity(intent)
        }
    }
}