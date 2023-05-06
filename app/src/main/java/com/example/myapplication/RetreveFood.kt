package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityFoodBinding
import com.example.myapplication.databinding.ActivityRetreveFoodBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class RetreveFood : AppCompatActivity() {

    private lateinit var binding : ActivityRetreveFoodBinding
    private lateinit var dbref : DatabaseReference
    private lateinit var itemRecyclerView: RecyclerView
    private lateinit var itemArrayList : ArrayList<Items>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetreveFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itemRecyclerView = binding.recycleView
        itemRecyclerView.layoutManager = LinearLayoutManager(this)
        itemRecyclerView.setHasFixedSize(true)

        itemArrayList = arrayListOf<Items>()
        getItemdata()
    }

    private fun getItemdata(){

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        println(userId)
        dbref = FirebaseDatabase.getInstance().getReference("users/$userId/foodItems")

        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){
                    itemArrayList.clear()
                    for (itemSnapshot in snapshot.children){
                        val item = itemSnapshot.getValue(Items::class.java)
                        itemArrayList.add(item!!)

                    }

                    itemRecyclerView.adapter = ItemAdapter(itemArrayList)

                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}