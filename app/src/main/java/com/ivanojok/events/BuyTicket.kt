package com.ivanojok.events

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import com.ivanojok.events.model.PrefsManager
import com.ivanojok.events.model.Ticket

class BuyTicket : AppCompatActivity() {

    private val prefsManager = PrefsManager.INSTANCE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_ticket)
        FirebaseApp.initializeApp(this)

        prefsManager.setContext(this.application)
        val phone = prefsManager.getUser().phone

        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        val image = intent.getStringExtra("image")
        val fees = intent.getStringExtra("fee")

        val eventName = findViewById<TextView>(R.id.event_name)
        val imageView = findViewById<ImageView>(R.id.imageView)

        val fee = findViewById<TextView>(R.id.fee)
        val fee2 = findViewById<TextView>(R.id.fee2)

        Glide.with(this).load(image).into(imageView)
        eventName.text = name
        fee.text = fees
        fee2.text = fees

        val airtel = findViewById<CardView>(R.id.airtel)
        val mtn = findViewById<CardView>(R.id.mtn)

        val phoned = findViewById<EditText>(R.id.phone)
        phoned.setText(phone)

        var selectedMethod: String = ""
        airtel.setOnClickListener {
            selectedMethod = "airtel"
            airtel.setCardBackgroundColor(resources.getColor(R.color.bg))
            mtn.setCardBackgroundColor(resources.getColor(R.color.white))
        }

        mtn.setOnClickListener {
            selectedMethod = "mtn"
            airtel.setCardBackgroundColor(resources.getColor(R.color.bg))
            mtn.setCardBackgroundColor(resources.getColor(R.color.white))
        }

        val buy = findViewById<Button>(R.id.buy)
        buy.setOnClickListener {
            if (selectedMethod == ""){
                Toast.makeText(this, "Select Payment Method", Toast.LENGTH_SHORT).show()
            }
            else{
                if (phone!!.isNotEmpty()){
                    var ref = FirebaseDatabase.getInstance().getReference("/tickets").child(phone).child(id!!).setValue(Ticket(id, name!!, fees!!, image!!, selectedMethod))
                    Toast.makeText(this, "Ticket Bought", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, Home::class.java))
                    finish()
                }
                else{
                    Toast.makeText(this, "Enter Correct Number", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}