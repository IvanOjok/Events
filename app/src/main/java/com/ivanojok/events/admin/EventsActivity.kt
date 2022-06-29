package com.ivanojok.events.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ivanojok.events.R
import com.ivanojok.events.model.Event
import com.ivanojok.events.model.EventsAdapter

class EventsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        FirebaseApp.initializeApp(this)

        val add = findViewById<ImageView>(R.id.add)
        add.setOnClickListener{
            startActivity(Intent(this, AddEventActivity::class.java))
        }

        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener{
            startActivity(Intent(this, AdminActivity::class.java))
        }


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val v = ArrayList<Event>()
        val ref = FirebaseDatabase.getInstance().getReference("/events")
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {

                            for (i in snapshot.children){
                                val z = i.getValue(Event::class.java)
                                v.add(z!!)
                            }

                            recyclerView.adapter = EventsAdapter(this@EventsActivity, v, "none")
                            recyclerView.layoutManager = LinearLayoutManager(this@EventsActivity)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })

    }
}