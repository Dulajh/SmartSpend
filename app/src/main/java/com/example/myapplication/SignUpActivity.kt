package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

// ... imports

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance().reference

        binding.textView.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val password = binding.passET.text.toString()
            val confirmPassword = binding.confirmPassEt.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == (confirmPassword)) {

                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val user = firebaseAuth.currentUser
                                val userId = user?.uid ?: ""
                                val email = user?.email ?: ""

                                val userData = User(userId, email) // create a User object with the new user's data
                                firebaseDatabase.child("users").child(userId).setValue(userData) // save the user data to the Realtime Database

                                val intent = Intent(this, SignInActivity::class.java)
                                startActivity(intent)
                                Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(this, task.exception.toString(), Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }

                } else {
                    Toast.makeText(this, "Password not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Required to fill all the columns", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

// define a User data class to represent the user data you want to save
data class User(val userId: String = "", val email: String = "")




//class SignUpActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivitySignUpBinding
//    private lateinit var firebaseAuth: FirebaseAuth
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivitySignUpBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        firebaseAuth = FirebaseAuth.getInstance()
//
//        binding.textView.setOnClickListener {
//            val intent = Intent(this, SignInActivity::class.java)
//            startActivity(intent)
//        }
//
//        binding.button.setOnClickListener {
//            val email = binding.emailEt.text.toString()
//            val password = binding.passET.text.toString()
//            val confirmPassword = binding.confirmPassEt.text.toString()
//
//            if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
//                if (password == (confirmPassword)) {
//
//                    firebaseAuth.createUserWithEmailAndPassword(email, password)
//                        .addOnCompleteListener {
//                            if (it.isSuccessful) {
//                                val intent = Intent(this, SignInActivity::class.java)
//                                startActivity(intent)
//                                Toast.makeText(this, "Successfull", Toast.LENGTH_SHORT).show()
//                            } else {
//                                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT)
//                                    .show()
//                            }
//                        }
//
//                } else {
//                    Toast.makeText(this, "Password not matching", Toast.LENGTH_SHORT).show()
//                }
//            } else {
//                Toast.makeText(this, "required to fill all the columns", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//}