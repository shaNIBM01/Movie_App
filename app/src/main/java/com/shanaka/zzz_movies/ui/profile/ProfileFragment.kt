package com.shanaka.zzz_movies.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.shanaka.zzz_movies.R

class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Find buttons
        val btnEditProfile = view.findViewById<Button>(R.id.btnEditProfile)
        val btnSettings = view.findViewById<Button>(R.id.btnSettings)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        // Set click listeners
        val toastMessage = "Feature soon to be deployed"
        val context = requireContext()

        btnEditProfile.setOnClickListener {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
        }

        btnSettings.setOnClickListener {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
        }

        btnLogout.setOnClickListener {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
