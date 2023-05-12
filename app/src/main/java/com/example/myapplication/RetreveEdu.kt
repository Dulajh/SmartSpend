package com.example.myapplication

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityRetreveEduBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class RetreveEdu : AppCompatActivity() {
    private lateinit var binding : ActivityRetreveEduBinding
    private lateinit var dbref : DatabaseReference
    private lateinit var itemRecyclerView: RecyclerView
    private lateinit var itemArrayList : ArrayList<Stetionaries>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetreveEduBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itemRecyclerView = binding.recycleView
        itemRecyclerView.layoutManager = LinearLayoutManager(this)
        itemRecyclerView.setHasFixedSize(true)




        itemArrayList = arrayListOf<Stetionaries>()
        getItemdata()
    }

    private fun getItemdata(){

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        println(userId)
        dbref = FirebaseDatabase.getInstance().getReference("users/$userId/Stetionaries")


        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){
                    itemArrayList.clear()
                    for (itemSnapshot in snapshot.children){
                        val Stetionaries = itemSnapshot.getValue(Stetionaries::class.java)
                        itemArrayList.add(Stetionaries!!)

                    }

                    itemRecyclerView.adapter = EduAdapter(itemArrayList)

                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }


//    private fun CalculateTotal(){
//        val userId = FirebaseAuth.getInstance().currentUser?.uid
//        val foodRef = FirebaseDatabase.getInstance().getReference("users/$userId/Stetionaries")
//        foodRef.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                var totalAmount = 0
//                for (foodSnapshot in dataSnapshot.children) {
//                    val amount = foodSnapshot.child("amount").getValue(String::class.java)
//                    if (amount != null) {
//                        totalAmount += amount.toInt()
//                    }
//                }
//                // Use the totalAmount here
//                Log.d(TAG, "Total amount: $totalAmount")
//                print("$totalAmount")
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Handle errors here
//            }
//        })
//    }

}