package com.ivanojok.events.admin

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.ivanojok.events.R
import com.ivanojok.events.model.Event

class AddEventActivity : AppCompatActivity() {

    //private val prefsManager = PrefsManager.INSTANCE

    private val CAPTURE_PERMISSION_CODE = 1000
    // private val IMAGE_CAPTURE_CODE = 1001
    val PICK_PERMISSION_CODE = 1002
    val IMAGE_PICK_CODE = 1003

    var image_uri: Uri? = null
    lateinit var b: ImageView
    var mAuth: FirebaseAuth?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)
        mAuth = FirebaseAuth.getInstance()

        val back = findViewById<ImageView>(R.id.imageView2)
        back.setOnClickListener {
            startActivity(Intent(this, AdminActivity::class.java))
            finish()
        }

        val add = findViewById<MaterialButton>(R.id.add)
        add.setOnClickListener {
            val f = findViewById<EditText>(R.id.first)
            val name = f.text.toString()

            val l = findViewById<EditText>(R.id.last)
            val location = l.text.toString()

            val createEmail = findViewById<EditText>(R.id.createEmail)
            val fee = createEmail.text.toString()

            val c = findViewById<EditText>(R.id.profession)
            val description = c.text.toString()

            val doc = findViewById<EditText>(R.id.dNo)
            val organizer = doc.text.toString()

            val ph = findViewById<EditText>(R.id.phone)
            val phone = ph.text.toString()

            val eh = findViewById<EditText>(R.id.eTime)
            val eTime = eh.text.toString()

            if (name.isNotEmpty() && location.isNotEmpty() && fee.isNotEmpty() && description.isNotEmpty() && organizer.isNotEmpty() && phone.isNotEmpty() && eTime.isNotEmpty() && image_uri != null){
                //uploadImage(phone, first + last,nin, lat, long)
                //Log.d("upload", "${uploadImage(phone, first + last,nin, lat, long)}")
            }
            else{
                Toast.makeText(this, "fill in all details", Toast.LENGTH_SHORT).show()
            }


        }


        b = findViewById(R.id.img)
        b.setOnClickListener {
            pickImageFromGallery()
        }
    }


    fun pickImageFromGallery(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions:Array<out String>, grantResults:IntArray) {
        //this method is called, when user presses Allow or Deny from Permission Request Popup
        when (requestCode) {
//            CAPTURE_PERMISSION_CODE -> {
//                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    openCamera()
//                } else {
//                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
//                }
//            }
            PICK_PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pickImageFromGallery()
                } else {
                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            image_uri = data!!.data
            //img.setImageURI(image_uri)

            val bitmap = MediaStore.Images.Media
                .getBitmap(
                    contentResolver,
                    image_uri
                )
            b.setImageBitmap(bitmap)
//            b.borderWidth = R.dimen._2sdp
//            b.borderColor = resources.getColor(R.color.backPurple)
        }
    }


    // UploadImage method
    fun uploadImage(name:String, location:String, fee: String, description:String, organizer:String, time: String, contact: String) {
        if (image_uri != null && mAuth!!.currentUser != null) {
            Log.d("image", image_uri.toString())

            //// && mAuth!!.currentUser != null

            // Defining the child of storageReference
            val sReference = FirebaseStorage.getInstance().getReference().child("/images")
                .child(image_uri.toString())
            val q = sReference.putFile(image_uri!!)
            val utask = q.continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception.let {
                        throw it!!
                    }
                } else {
                    val t = sReference.downloadUrl
                    dbStore(name, location, fee, t.toString(), description, organizer, time, contact)
                    sReference.downloadUrl

                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val imgUrl = task.result.toString()
                        dbStore(name, location, fee, imgUrl, description, organizer, time, contact)
                    } else {
                        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
            else{

                Log.d("image", image_uri.toString())

                Log.d("anonymous", "ddd")
            signInAnonymously(name, location, fee, description, organizer, time, contact)

            }
        }

    private fun signInAnonymously(name:String, location:String, fee: String, description:String, organizer:String, time: String, contact: String ) {

        mAuth!!.signInAnonymously().addOnSuccessListener(this, OnSuccessListener<AuthResult?> {
            // Defining the child of storageReference
            val sReference = FirebaseStorage.getInstance().getReference().child("/images").child(image_uri.toString())
            val q = sReference.putFile(image_uri!!)

            val utask = q.continueWithTask{ task ->
                if (!task.isSuccessful){
                    task.exception.let {
                        throw it!!
                    }
                }
                else{
                    val t = sReference.downloadUrl
                    dbStore(name, location, fee, t.toString(), description, organizer, time, contact)
                }
                sReference.downloadUrl

            }.addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val imgUrl = task.result.toString()
                    dbStore(name, location, fee, imgUrl, description, organizer, time, contact)
                }
                else{
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
                }
            }

        })
            .addOnFailureListener(this,
                OnFailureListener { exception ->
                    Log.e(
                        "Storage",
                        "signInAnonymously:FAILURE",
                        exception
                    )
                })

    }

    fun dbStore(name:String, location:String, fee: String, imgUrl:String, description:String, organizer:String, time: String, contact: String ){
        val db = FirebaseDatabase.getInstance().getReference("/events").push()
                val k = db.key
                val s = db.setValue(
                    Event(k!!, name, location, fee, imgUrl, description , organizer , time, contact)
                )
        if (s.isCanceled) {
            Toast.makeText(this, "An error occurred", Toast.LENGTH_LONG)
                .show()
        } else {
            startActivity(Intent(this, EventsActivity::class.java))
            finish()
        }
    }



}