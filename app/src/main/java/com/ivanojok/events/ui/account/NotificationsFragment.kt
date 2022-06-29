package com.ivanojok.events.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.FirebaseApp
import com.ivanojok.events.Login
import com.ivanojok.events.R
import com.ivanojok.events.databinding.FragmentNotificationsBinding
import com.ivanojok.events.model.PrefsManager

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val prefsManager = PrefsManager.INSTANCE


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val name = root.findViewById<TextView>(R.id.name)
        val phone = root.findViewById<TextView>(R.id.id)
        val nin = root.findViewById<TextView>(R.id.course)
        val residence = root.findViewById<TextView>(R.id.email)
        val imageView7 = root.findViewById<ImageView>(R.id.imageView7)


        if (prefsManager.isLoggedIn()) {
            val victim = prefsManager.getUser()

            name.text = "${victim.firstName}  ${victim.lastName}"
            phone.text = "${victim.phone}"
            nin.text = "NIN: ${victim.nin}"
            residence.text = "Residence: ${victim.residence}"

        }



        val logout = root.findViewById<Button>(R.id.logout)
        logout.setOnClickListener {
            if(prefsManager.isLoggedIn()) {
                prefsManager.onVictimLogout()
            }
            startActivity(Intent(requireContext(), Login::class.java))
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}