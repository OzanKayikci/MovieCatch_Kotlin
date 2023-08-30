package com.example.moviecatch.ui.fragments.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.moviecatch.R
import com.example.moviecatch.databinding.FragmentMainBinding
import com.example.moviecatch.databinding.FragmentSplashBinding
import com.example.moviecatch.prefs.SessionManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        if (sessionManager.isFirstRun) {
            sessionManager.isFirstRun = false
        }
        auth = Firebase.auth
        setupTabBar()
        checkLoggedInState()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupTabBar() {
        //bottom'un hareket özelliğini home_nav ile eşleştirdik, oradaki fragmentler üyelerin dedik
        binding.bottomNavBar.setItemSelected(R.id.nav_home, true)
        //oradaki sıralamayı söylüyoruz
        binding.bottomNavBar.setOnItemSelectedListener {
            when (it) {
                R.id.nav_home -> childFragmentManager.primaryNavigationFragment?.findNavController()
                    ?.navigate(R.id.moviesPagesFragment)

                R.id.nav_favorites -> childFragmentManager.primaryNavigationFragment?.findNavController()
                    ?.navigate(R.id.favoritePagesFragment)

                R.id.nav_settings -> childFragmentManager.primaryNavigationFragment?.findNavController()
                    ?.navigate(R.id.settingsFragment)
            }
        }
    }

    private fun checkLoggedInState() {
        if (auth.currentUser == null) {
            Log.d("user", "no user logged in")
        } else {
            Log.d("user", auth.currentUser!!.email.toString())
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }

        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this, callback
        )
    }
}