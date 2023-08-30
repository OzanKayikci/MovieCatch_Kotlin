package com.example.moviecatch.ui.fragments.auth

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.moviecatch.R
import com.example.moviecatch.databinding.FragmentResetPasswordBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordFragment : Fragment() {
    private var _binding: FragmentResetPasswordBinding? = null
    private val binding get() = _binding!!
    private var email: String? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResetPasswordBinding.inflate(inflater)
        val view = binding.root
        binding.emailText.setText(arguments?.getString("email"))
        buttonHandle()
        return view
    }

    private fun buttonHandle() {
        binding.resetButton.alpha = 0.5f
        binding.resetButton.isClickable = false

        binding.resetButton.setOnClickListener {
            if (checkEmail()) sendResetMail(binding.emailText.text.toString())
        }
        binding.emailText.doAfterTextChanged {
            checkEmail()
        }
    }

    private fun checkEmail(): Boolean {

        if (emailValidator(binding.emailText)) {
            binding.emailLayout.helperText = ("Please enter a correct email address")
            binding.resetButton.alpha = 0.5f
            binding.resetButton.isClickable = false
            return false
        }
        binding.emailLayout.helperText = null
        binding.resetButton.alpha = 1f
        binding.resetButton.isClickable = true
        return true


    }

    private fun emailValidator(etMail: EditText): Boolean {

        val emailToText = etMail.text.toString()

        return !(emailToText.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText)
            .matches())
    }

    private fun sendResetMail(email: String) {
        Firebase.auth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                    Toast.makeText(
                        requireContext(),
                        "Reset password mail sent to your mail",
                        Toast.LENGTH_LONG
                    ).show()
                    findNavController().navigate(R.id.action_resetPasswordFragment_to_singInFragment)
                }
            }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}