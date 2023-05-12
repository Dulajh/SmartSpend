package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityRetreveAccomBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class RetreveAccom : AppCompatActivity() {

    private lateinit var binding : ActivityRetreveAccomBinding
    private lateinit var dbref : DatabaseReference
    private lateinit var itemRecyclerView: RecyclerView
    private lateinit var itemArrayList : ArrayList<Accomndantions>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetreveAccomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itemRecyclerView = binding.recycleView
        itemRecyclerView.layoutManager = LinearLayoutManager(this)
        itemRecyclerView.setHasFixedSize(true)

        itemArrayList = arrayListOf<Accomndantions>()
        getItemdata()
    }

    private fun getItemdata(){

        val userId = FirebaseAuth.getInstance().currentUser?.uid
        println(userId)
        dbref = FirebaseDatabase.getInstance().getReference("users/$userId/Accomondation")

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists()){
                    itemArrayList.clear()
                    for (itemSnapshot in snapshot.children){
                        val Accomndantions = itemSnapshot.getValue(Accomndantions::class.java)
                        itemArrayList.add(Accomndantions!!)

                    }

                    itemRecyclerView.adapter = AccomAdepter(itemArrayList)

                }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }
}