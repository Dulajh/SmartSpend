package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityAcomnBinding
import com.example.myapplication.databinding.ActivityFoodBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ActivityAcomn : AppCompatActivity() {
    private lateinit var binding: ActivityAcomnBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAcomnBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.reTbutton.setOnClickListener {
            val intent = Intent(this, RetreveFood::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            val accomndation = binding.foodEt.text.toString()
            val amount = binding.amountEt.text.toString()
            val description = binding.descriptionEt.text.toString()

            if (accomndation.isNotEmpty() && amount.isNotEmpty() && description.isNotEmpty()) {

                val userId = firebaseAuth.currentUser?.uid

                val databaseRef =
                    FirebaseDatabase.getInstance().reference.child("users").child(userId!!)
                        .child("Accomondation").push()

                val foodItem = HashMap<String, Any>()
                foodItem["food"] = accomndation
                foodItem["amount"] = amount
                foodItem["description"] = description

                databaseRef.setValue(foodItem).addOnCompleteListener {
                    if (it.isSuccessful) {
                        finish()
                        Toast.makeText(this, "data insert successful", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                    }
                }

            } else {
                Toast.makeText(this, "required to fill all the columns", Toast.LENGTH_SHORT).show()
            }
        }
    }
}