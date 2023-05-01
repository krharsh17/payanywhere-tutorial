package com.example.mypaymentapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypaymentapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpView()

    }

    private fun setUpView() {
        val items: ArrayList<CartItem> = ArrayList()

        items.add(CartItem("Coffee beans", "Lorem ipsum dolor sit amet...", 1.5f, 2))
        items.add(CartItem("Sugar", "Lorem ipsum dolor sit amet...", 0.3f, 1))
        items.add(CartItem("Mug", "Lorem ipsum dolor sit amet...", 3f, 1))
        items.add(CartItem("Cup", "Lorem ipsum dolor sit amet...", 1.2f, 2))

        setUpList(items)

        var total = 0f

        items.forEach(action = {
            total += it.price
        })

        setUpButton(total)

    }

    private fun setUpList(items: ArrayList<CartItem>) {
        val cartRecyclerView = findViewById<RecyclerView>(R.id.cart_recyclerview)

        cartRecyclerView.layoutManager = LinearLayoutManager(this)
        cartRecyclerView.adapter = CartRecyclerAdapter(items)
    }

    private fun setUpButton(total: Float) {
        val checkoutButton = findViewById<Button>(R.id.checkout_button)
        checkoutButton.text = "Checkout (\$$total)"
        checkoutButton.setOnClickListener {
            createPaymentIntent(total)
        }
    }


    private fun createPaymentIntent(total: Float) {
        val paymentUrl = "payanywhere://payment/"
        val paymentRequestCode = 123

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$paymentUrl?chargeAmount=$total"))
        startActivityForResult(intent, paymentRequestCode)
    }
}