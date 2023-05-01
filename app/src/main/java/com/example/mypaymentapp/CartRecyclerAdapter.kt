package com.example.mypaymentapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class CartRecyclerAdapter(private val dataSet: ArrayList<CartItem>) :
    RecyclerView.Adapter<CartRecyclerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView
        val description: TextView
        val quantity: TextView
        val price: TextView
        val image: ImageView

        init {
            // Define click listener for the ViewHolder's View
            title = view.findViewById(R.id.title_textview)
            description = view.findViewById(R.id.description_textview)
            quantity = view.findViewById(R.id.quantity_textview)
            price = view.findViewById(R.id.cost_textview)
            image = view.findViewById(R.id.imageview)
        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.cart_item, viewGroup, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        viewHolder.title.text = dataSet[position].title
        viewHolder.description.text = dataSet[position].description
        viewHolder.quantity.text = "Qty: ${dataSet[position].quantity}"
        viewHolder.price.text = "Item Total: $ ${dataSet[position].price}"

        when (dataSet[position].title.lowercase(Locale.getDefault())) {
            "coffee beans" -> viewHolder.image.setImageResource(R.drawable.coffee)
            "mug" -> viewHolder.image.setImageResource(R.drawable.mug)
            "cup" -> viewHolder.image.setImageResource(R.drawable.cup)
            "sugar" -> viewHolder.image.setImageResource(R.drawable.sugar)
        }

    }

    override fun getItemCount() = dataSet.size

}