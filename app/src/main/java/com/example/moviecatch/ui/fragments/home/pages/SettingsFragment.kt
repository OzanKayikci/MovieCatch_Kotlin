package com.example.moviecatch.ui.fragments.home.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviecatch.databinding.FragmentFavoriteBinding
import com.example.moviecatch.databinding.FragmentSettingsBinding
import com.example.moviecatch.util.AvatarCanvas
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and onDestroyView.
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        auth = Firebase.auth
        setComponents()
        buttonHandle()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setComponents() {
        binding.avatar.setImageBitmap(AvatarCanvas(auth.currentUser!!.email.toString()).getAvatar())
        binding.userName.text = auth.currentUser!!.email
    }

    private fun buttonHandle() {
        binding.signOut.setOnClickListener {
            auth.signOut()
            requireActivity().finish() // Mevcut aktiviteyi sonlandır
            val intent = requireActivity().intent // Mevcut aktivitenin intent'ini al
            requireActivity().startActivity(intent) // Mevcut aktiviteyi tekrar başlat
        }
    }

}