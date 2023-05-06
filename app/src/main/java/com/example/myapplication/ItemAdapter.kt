package com.example.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter(private val itemList: ArrayList<Items>) : RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var food: TextView = itemView.findViewById(R.id.Item)
        var amount: TextView = itemView.findViewById(R.id.amount)
        var description: TextView = itemView.findViewById(R.id.description)
        var mMenus: ImageView = itemView.findViewById(R.id.mMenu)

        init {
            mMenus.setOnClickListener {
                popupMenus(itemView.context, adapterPosition,itemView)
            }
        }
    }

    private fun popupMenus(context: Context, position: Int,itemView: View) {
        val item = itemList[position]
        val popupMenus = PopupMenu(context, itemView)
        popupMenus.inflate(R.menu.show_menu)
        popupMenus.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.editText -> {
                    val dialogView = LayoutInflater.from(context).inflate(R.layout.add_item, null)
                    val nameEt = dialogView.findViewById<EditText>(R.id.foodEt)
                    val amountEt = dialogView.findViewById<EditText>(R.id.amountEt)
                    val descriptionEt = dialogView.findViewById<EditText>(R.id.descriptionEt)
                    nameEt.setText(item.food)
                    amountEt.setText(item.amount)
                    descriptionEt.setText(item.description)

                    val dialogBuilder = AlertDialog.Builder(context)
                        .setView(dialogView)
                        .setPositiveButton("OK") { dialog, _ ->
                            itemList[position].food = nameEt.text.toString()
                            itemList[position].amount = amountEt.text.toString()
                            itemList[position].description = descriptionEt.text.toString()
                            notifyDataSetChanged()
                            Toast.makeText(context, "Item updated", Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                        }
                        .setNegativeButton("Cancel") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .create()
                    dialogBuilder.show()

                    true
                }
                R.id.deleteText -> {
                    itemList.removeAt(position)
                    notifyDataSetChanged()
                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> true
            }
        }
        popupMenus.show()
        val popup = PopupMenu::class.java.getDeclaredField("mPopup")
        popup.isAccessible = true
        val menu = popup.get(popupMenus)
        menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java).invoke(menu, true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = itemList[position]
        holder.food.text = currentitem.food
        holder.amount.text = currentitem.amount
        holder.description.text = currentitem.description
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}



















//package com.example.myapplication
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.EditText
//import android.widget.ImageView
//import android.widget.PopupMenu
//import android.widget.TextView
//import android.widget.Toast
//import androidx.recyclerview.widget.RecyclerView
//
//class ItemAdapter(private val itemList: ArrayList<Items>) : RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {
//
//    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        var food: TextView = itemView.findViewById(R.id.Item)
//        var amount: TextView = itemView.findViewById(R.id.amount)
//        var description: TextView = itemView.findViewById(R.id.description)
//        var mMenus: ImageView = itemView.findViewById(R.id.mMenu)
//
//        init {
//            mMenus.setOnClickListener {
//                popupMenus(itemView.context, it)
//            }
//        }
//    }
//
//    private fun popupMenus(context: Context, itemView: View){
//        val position = itemList[adapterPosition]
//        val popupMenus = PopupMenu(context, itemView)
//        popupMenus.inflate(R.menu.show_menu)
//        popupMenus.setOnMenuItemClickListener {
//            when (it.itemId) {
//                R.id.editText -> {
//                    val context = LayoutInflater.from(context).inflate(R.layout.add_item,null).AlertDialog.bulder(context)
//                    val name = itemView.findViewById<EditText>(R.id.foodEt)
//                    val amount = itemView.findViewById<EditText>(R.id.amountEt)
//                    val description = itemView.findViewById<EditText>(R.id.descriptionEt)
//                        .setView(itemView)
//                        .setPositiveButton("ok"){
//                            dialog, ->
//                            position.food = name.text.toString()
//                            position.amount = amount.text.toString()
//                            notifyDataSetChanged()
//                            Toast.makeText(context,"Edit text updated " , Toast.LENGTH_SHORT).show()
//                            dialog.dismiss()
//                        }
//                        .setNegativeButton("ok"){
//                            dialog, ->
//                            dialog.dismiss()
//                        }
//                        .create()
//                        .show()
//
//                    Toast.makeText(context,"Edit text button is clicked " , Toast.LENGTH_SHORT).show()
//                    true
//                }
//                R.id.deleteText -> {
//                    Toast.makeText(context,"Delete button is clicked" , Toast.LENGTH_SHORT).show()
//                    true
//                }
//                else -> true
//            }
//
//        }
//        popupMenus.show()
//        val popup = PopupMenu::class.java.getDeclaredField("mPopup")
//        popup.isAccessible = true
//        val menu =  popup.get(popupMenus)
//        menu.javaClass.getDeclaredMethod("setForceShowIcon",Boolean::class.java).invoke(menu,true)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.items, parent, false)
//        return MyViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val currentitem = itemList[position]
//        holder.food.text = currentitem.food
//        holder.amount.text = currentitem.amount
//        holder.description.text = currentitem.description
//    }
//
//    override fun getItemCount(): Int {
//        return itemList.size
//    }
//}







//
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//
//
//class ItemAdapter(private val itemList: ArrayList<Items>) : RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {
//
//   inner class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
//
//        var food : TextView
//        var amount : TextView
//        var description : TextView
//        lateinit var mMenus : ImageView
//
//
//        init {
//            food = itemView.findViewById(R.id.Item)
//            amount = itemView.findViewById(R.id.amount)
//            description = itemView.findViewById(R.id.description)
//            mMenus.setOnClickListener(popupMenus(it))
//        }
//
//   }
//    private fun popupMenus(itemView: View){
//        val popupMenus = popupMenus(c,itemView)
//        popupMenus.inflate(R.menu.show_menu)
//        popupMenus.setOnMenuItemClickListener{
//        when(it.itemId){
//            R.id.editText -> {
//                true
//            }
//            R.id.deleteText -> {
//                true
//            }
//            else-> true
//        }
//    }
//        popupMenus.show()
//
//}
//
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.items,
//        parent,false)
//
//        return MyViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val currentitem =   itemList[position]
//
//        holder.food.text =  currentitem.food
//        holder.amount.text = currentitem.amount
//        holder.description.text = currentitem.description
//    }
//
//    override fun getItemCount(): Int {
//        return itemList.size
//    }
//
//}