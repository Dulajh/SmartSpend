package com.example.myapplication

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.myapplication.databinding.ActivityFoodBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ActivityFood : AppCompatActivity() {
    
    private lateinit var binding: ActivityFoodBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var ExpenceFOTpoic : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CalculateTotal()
        firebaseAuth = FirebaseAuth.getInstance()
        ExpenceFOTpoic = binding.ExpenceFOTpoic

        binding.reTbutton.setOnClickListener {
            val intent = Intent(this, RetreveFood::class.java)
            startActivity(intent)
        }


        binding.button.setOnClickListener {
            val food = binding.foodEt.text.toString()
            val amount = binding.amountEt.text.toString()
            val description = binding.descriptionEt.text.toString()

            if (food.isNotEmpty() && food.isNotEmpty() && food.isNotEmpty()) {

                val userId = firebaseAuth.currentUser?.uid

                val databaseRef =
                    FirebaseDatabase.getInstance().reference.child("users").child(userId!!)
                        .child("foodItems").push()

                val foodItem = HashMap<String, Any>()
                foodItem["food"] = food
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
        CalculateTotal()
    }

    private fun CalculateTotal(){
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val foodRef = FirebaseDatabase.getInstance().getReference("users/$userId/foodItems")
        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var totalAmount = 0.0 // change data type to Double
                for (foodSnapshot in dataSnapshot.children) {
                    val amount = foodSnapshot.child("amount").getValue(String::class.java)
                    if (amount != null) {
                        totalAmount += amount.toDouble() // use toDouble() to parse the string value to Double
                    }
                }
                // Use the totalAmount here
                Log.d(ContentValues.TAG, "Total amount: $totalAmount")
                print("$totalAmount")
                ExpenceFOTpoic.text = "Rs: $totalAmount"
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle errors here
            }
        })
    }
}