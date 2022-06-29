package com.ivanojok.events

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ivanojok.events.model.ChatAdapter
import com.ivanojok.events.model.PrefsManager

class EventDetails : AppCompatActivity() {
    private val prefsManager = PrefsManager.INSTANCE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        FirebaseApp.initializeApp(this)

        prefsManager.setContext(this.application)
        val phone = prefsManager.getUser().phone

        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        val image = intent.getStringExtra("image")
        val location = intent.getStringExtra("location")
        val fee = intent.getStringExtra("fee")
        val description = intent.getStringExtra("description")
        val organizer = intent.getStringExtra("organizer")
        val time = intent.getStringExtra("time")
        val contact = intent.getStringExtra("contact")

        val n = findViewById<TextView>(R.id.event_name)
        n.text = name
        val i = findViewById<ImageView>(R.id.imageView)
        Glide.with(this).load(image).into(i)
        val l = findViewById<TextView>(R.id.location)
        l.text = location
        val f = findViewById<TextView>(R.id.fee)
        f.text = fee
        val t = findViewById<TextView>(R.id.time)
        t.text = time
        val d = findViewById<TextView>(R.id.description)
        d.text = description
        val o = findViewById<TextView>(R.id.organizer)
        o.text = organizer
        val c = findViewById<TextView>(R.id.contact)
        c.text = contact


        val db = FirebaseDatabase.getInstance()
        val buy = findViewById<Button>(R.id.buy)
        buy.setOnClickListener {
            val intent = Intent(this, BuyTicket::class.java)
            intent.putExtra("id", id)
            intent.putExtra("name", name)
            intent.putExtra("image", image)
            intent.putExtra("fee", fee)
            startActivity(intent)
            //db.getReference("/tickets").child(phone!!).child(id!!).setValue("g")
        }

        val comment = findViewById<RecyclerView>(R.id.comment)
        db.getReference("/comments").child(id!!).addValueEventListener(
            object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val names = ArrayList<String>()
                        //val c = HashMap<String, Book>()
                        val r = snapshot.value as HashMap<String, String>

                        val k = r.keys
                        val comments = ArrayList<String>()
                        for (x in k) {
                            names.add(x)
                            comments.add(r[x]!!)


                            val bAdapter = ChatAdapter(
                                this@EventDetails,
                                names,
                                comments
                            )

                            comment.adapter = bAdapter
                            comment.layoutManager = LinearLayoutManager(this@EventDetails)
                        }
                    }
                    else{
                        val no = findViewById<TextView>(R.id.no_comment)
                        no.visibility = View.VISIBLE
                        no.text = "No comments. Be the first to comment"
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            }
        )


        val send = findViewById<ImageView>(R.id.send)
        val message = findViewById<EditText>(R.id.message)
        send.setOnClickListener {
            val m = message.text.toString()
            if (m == ""){
                return@setOnClickListener
            }

            val userName = prefsManager.getUser().firstName + prefsManager.getUser().lastName
            val ref = db.getReference("/comments").child(id).setValue(hashMapOf(Pair(userName, m)))

            if (ref.isCanceled){
                Toast.makeText(this, "An error occurred", Toast.LENGTH_LONG).show()
            }
            else{
                startActivity(intent)
            }
        }
    }
}