package com.ivanojok.events.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ivanojok.events.R
import com.ivanojok.events.databinding.FragmentDashboardBinding
import com.ivanojok.events.model.Event
import com.ivanojok.events.model.EventsAdapter
import com.ivanojok.events.model.PrefsManager

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val prefsManager = PrefsManager.INSTANCE

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        FirebaseApp.initializeApp(requireContext())


        val phone = prefsManager.getUser().phone

        val recyclerView = root.findViewById<RecyclerView>(R.id.recyclerView)
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

                            recyclerView.adapter = EventsAdapter(requireContext(), v, phone!!)
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