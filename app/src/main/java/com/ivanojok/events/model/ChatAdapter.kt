package com.ivanojok.events.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ivanojok.events.R

class ChatAdapter(context: Context, name: ArrayList<String>, comment:ArrayList<String>) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>(){
    var c = context
    var y = name
    var z = comment


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatViewHolder {
        var inflate: View ?= null

       return ChatViewHolder(LayoutInflater.from(c).inflate(R.layout.from_message_layout, parent, false))
    }

    inner class ChatViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(context: Context, name:String, message:String){

            val n = itemView.findViewById<TextView>(R.id.message)
            n.text = name

            val d = itemView.findViewById<TextView>(R.id.time)
            d.text = message

        }

    }

    override fun getItemCount(): Int {
        return y.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.onBind(c, y[position], z[position])
    }

}