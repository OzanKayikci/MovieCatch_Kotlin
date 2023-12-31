package com.example.moviecatch.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.moviecatch.R
import com.example.moviecatch.databinding.FragmentSplashBinding
import com.example.moviecatch.prefs.SessionManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)

        auth = Firebase.auth
        Handler(Looper.getMainLooper()).postDelayed({
            if (sessionManager.isFirstRun) {
                findNavController().navigate(R.id.action_splashFragment_to_appIntroFragment)
            } else {
                findNavController().navigate(
                    if (auth.currentUser !== null)
                        R.id.action_splashFragment_to_mainFragment
                    else
                        R.id.action_splashFragment_to_singInFragment

                )

            }
        }, 1000)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}