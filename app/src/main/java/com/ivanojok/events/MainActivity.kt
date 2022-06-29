package com.ivanojok.events

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.FirebaseApp
import com.ivanojok.events.model.PrefsManager

class MainActivity : AppCompatActivity() {

    private val prefsManager = PrefsManager.INSTANCE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)

        prefsManager.setContext(this.application)

        Handler().postDelayed({
            if(prefsManager.isLoggedIn()){
                startActivity(Intent(this, Home::class.java))
                finish()
            }
            else{
                startActivity(Intent(this, Login::class.java))
                finish()
            }
        }
        , 3000)


    }
}