package com.ivanojok.events

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase
import com.ivanojok.events.model.PrefsManager
import com.ivanojok.events.model.User
import java.util.*

class Register : AppCompatActivity() {
    private val prefsManager = PrefsManager.INSTANCE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        FirebaseApp.initializeApp(this)

        prefsManager.setContext(this.application)
        val phone = intent.getStringExtra("phone")

        val create = findViewById<Button>(R.id.create)
        create.setOnClickListener {

            val f = findViewById<EditText>(R.id.first)
            val first = f.text.toString()

            val l = findViewById<EditText>(R.id.last)
            val last = l.text.toString()

            val createEmail = findViewById<EditText>(R.id.createEmail)
            val email = createEmail.text.toString()

            val c = findViewById<EditText>(R.id.course)
            val course = c.text.toString()


            if (first.isNotEmpty() && last.isNotEmpty() && email.isNotEmpty() && course.isNotEmpty() ){


                val db = FirebaseDatabase.getInstance()
                //val id = UUID.randomUUID().toString()

                val victim = User(phone!!, first, last, email, course)
                val ref = db.getReference("/users").child(phone).setValue(victim)

                if (ref.isCanceled){
                    Toast.makeText(this, "An error occurred", Toast.LENGTH_LONG).show()
                }
                else{
                    prefsManager.onLogin(victim)
                    startActivity(Intent(this, Home::class.java))
                    finish()
                }
            }
            else{
                Toast.makeText(this, "Kindly fill in the correct credentials", Toast.LENGTH_LONG).show()
            }
        }

        val b = findViewById<ImageView>(R.id.imageView2)
        b.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
            finish()
        }

    }
}