package com.ivanojok.events.model

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ivanojok.events.EventDetails
import com.ivanojok.events.Home
import com.ivanojok.events.R

class EventsAdapter(r: Context, options: ArrayList<Event>, phone:String) : RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {
    var c = r
    var y = options
    var p = phone
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EventViewHolder {
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.event_list_layout, parent, false)
        return EventViewHolder(inflate)
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val card = itemView.findViewById<CardView>(R.id.card)

        fun onBind(context: Context, name: String, image: String, location: String, fee:String) {

            val n = itemView.findViewById<TextView>(R.id.event_name)
            n.text = name

            val iV = itemView.findViewById<ImageView>(R.id.imageView)
            Glide.with(context).load(image).into(iV)

            val d = itemView.findViewById<TextView>(R.id.location)
            d.text = location

            val f = itemView.findViewById<TextView>(R.id.fee)
            f.text = fee
        }

    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        Log.d("y", "$y")
        Log.d("c", "$c")
        holder.onBind(c, y[position].name!!, y[position].image!!, y[position].location!!, y[position].fee!!)

        holder.itemView.setOnClickListener {
           if (p != "none"){
               val intent = Intent(c, EventDetails::class.java)
               intent.putExtra("id", y[position].id)
               intent.putExtra("name", y[position].name)
               intent.putExtra("image", y[position].image)
               intent.putExtra("location", y[position].location)
               intent.putExtra("fee", y[position].fee)
               intent.putExtra("description", y[position].description)
               intent.putExtra("organizer", y[position].organizer)
               intent.putExtra("time", y[position].time)
               intent.putExtra("contact", y[position].contact)
               c.startActivity(intent)
           }
        }

    }

    override fun getItemCount(): Int {
        return y.size
    }
}

