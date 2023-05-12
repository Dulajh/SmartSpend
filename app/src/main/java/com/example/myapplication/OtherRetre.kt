package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityOtherRetreBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class OtherRetre : AppCompatActivity() {

    private lateinit var binding : ActivityOtherRetreBinding
    private lateinit var dbref : DatabaseReference
    private lateinit var itemRecyclerView: RecyclerView
    private lateinit var itemArrayList : ArrayList<Others>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtherRetreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itemRecyclerView = binding.recycleView
        itemRecyclerView.layoutManager = LinearLayoutManager(this)
        itemRecyclerView.setHasFixedSize(true)

        itemArrayList = arrayListOf<Others>()
        getItemdata()
    }

    private fun getItemdata(){

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        println(userId)
        dbref = FirebaseDatabase.getInstance().getReference("users/$userId/Other")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){
                    itemArrayList.clear()
                    for (itemSnapshot in snapshot.children){
                        val Others = itemSnapshot.getValue(Others::class.java)
                        itemArrayList.add(Others!!)

                    }

                    itemRecyclerView.adapter = OtherAdapter(itemArrayList)

                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}