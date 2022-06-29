package com.ivanojok.events

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.ivanojok.events.admin.AdminActivity
import com.ivanojok.events.model.PrefsManager

class Login : AppCompatActivity() {

    private val prefsManager = PrefsManager.INSTANCE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        prefsManager.setContext(this.application)
        FirebaseApp.initializeApp(this)


//        val back = findViewById<ImageView>(R.id.back)
//        back.setOnClickListener {
//            startActivity(Intent(this, SignUp::class.java))
//        }


        val next = findViewById<Button>(R.id.next)

        val phone = findViewById<EditText>(R.id.phone)


        next.setOnClickListener {
            val tel = phone.text.toString()

            if (tel == "admin"){
                startActivity(Intent(this, AdminActivity::class.java))
                finish()
            }
            else
                if (tel.isNotEmpty() && tel.length == 13) {

                    val intent = Intent(this, Verification::class.java)
                    intent.putExtra("phone", tel)
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(this, "Enter phone number", Toast.LENGTH_SHORT).show()
                }
        }
    }
}