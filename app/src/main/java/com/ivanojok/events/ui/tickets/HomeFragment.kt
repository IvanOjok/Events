package com.ivanojok.events.ui.tickets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ivanojok.events.R
import com.ivanojok.events.databinding.FragmentHomeBinding
import com.ivanojok.events.model.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val prefsManager = PrefsManager.INSTANCE

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val phone = prefsManager.getUser().phone

        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
        val v = ArrayList<Ticket>()
        val ref = FirebaseDatabase.getInstance().getReference("/tickets")
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {

                            for (i in snapshot.children){
                                val z = i.getValue(Ticket::class.java)
                                v.add(z!!)
                            }

                            recyclerView.adapter = TicketAdapter(requireContext(), v, phone!!)
                            recyclerView.layoutManager = LinearLayoutManager(requireContext())
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}