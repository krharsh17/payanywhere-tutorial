package com.example.mypaymentapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypaymentapp.databinding.ActivityMainBinding
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val TAG = "MyPaymentApp"
    private var paymentRequestCode = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG,"onActivityResult requestCode " + requestCode + " resultCode " + resultCode);
        if(requestCode == paymentRequestCode){
            if(resultCode == RESULT_OK){
                Log.d(TAG,"result ok");
                handleTransactionResponse(data);
            } else if(resultCode == RESULT_CANCELED){
                Log.d(TAG,"result cancelled");
                handleTransactionResponse(data);
            }
        }
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
        paymentRequestCode = 123

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("$paymentUrl?itemName=Coffee%20Store%20Total&chargeAmount=$total"))
        startActivityForResult(intent, paymentRequestCode)
    }

    private val EXTRA_TRANSACTION_RESULT = "transactionResult"
    private val EXTRA_TRANSACTION_UNIQUE_ID = "transactionUniqueId"
    private val EXTRA_RECEIPT_ID = "receiptId"
    private val EXTRA_PARTIAL_AUTH = "isPartialAuth"
    private val EXTRA_REQUESTED_AMOUNT = "requestedAmount"
    private val EXTRA_AUTHORIZED_AMOUNT = "authorizedAmount"
    private val EXTRA_TRANSACTION_ACTION = "transactionAction"
    private val EXTRA_CUSTOMER_TRANSACTION_ID = "customerTransactionId"
    private val EXTRA_INVOICE_NUMER = "invoiceNumber"


    private fun handleTransactionResponse(data: Intent?) {
        if(data != null){
            val transactionResult = data.getStringExtra(EXTRA_TRANSACTION_RESULT);
            val transactionUniqueId = data.getStringExtra(EXTRA_TRANSACTION_UNIQUE_ID);
            val receiptId = data.getLongExtra(EXTRA_RECEIPT_ID, 0);
            val customerTransactionId = data.getStringExtra(EXTRA_CUSTOMER_TRANSACTION_ID);
            val invoiceNumber = data.getStringExtra(EXTRA_INVOICE_NUMER);
            val transactionAction = data.getStringExtra(EXTRA_TRANSACTION_ACTION);
            val isPartialAuth = data.getBooleanExtra(EXTRA_PARTIAL_AUTH, false);

            var requestedAmount: BigDecimal? = null;
            if(data.getSerializableExtra(EXTRA_REQUESTED_AMOUNT) != null){
                requestedAmount = data.getSerializableExtra(EXTRA_REQUESTED_AMOUNT) as BigDecimal?;
            }
            var authorizedAmount: BigDecimal? = null;
            if(data.getSerializableExtra(EXTRA_AUTHORIZED_AMOUNT) != null){
                authorizedAmount = data.getSerializableExtra(EXTRA_AUTHORIZED_AMOUNT) as BigDecimal
            }

            Log.i(TAG, "Transaction Result: $transactionResult")
            Log.i(TAG, "Transaction Unique ID: $transactionUniqueId")
            Log.i(TAG, "Receipt ID: $receiptId")
            Log.i(TAG, "Customer Transaction ID: $customerTransactionId")
            Log.i(TAG, "Invoice Number: $invoiceNumber")
            Log.i(TAG, "Transaction Action: $transactionAction")
            Log.i(TAG, "Is Partial Auth?: $isPartialAuth")
            Log.i(TAG, "Requested Amount:$requestedAmount")
            Log.i(TAG, "Authorized Amount: $authorizedAmount")
        }
    }

}
