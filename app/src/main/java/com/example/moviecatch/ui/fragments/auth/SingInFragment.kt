package com.example.moviecatch.ui.fragments.auth

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.moviecatch.R
import com.example.moviecatch.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SingInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignInBinding.inflate(layoutInflater)
        val view = binding.root
        binding.emailText.setText(arguments?.getString("email"))
        auth = Firebase.auth

        buttonsHandle()
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun buttonsHandle() {

        binding.singInButton.isClickable = false
        binding.singInButton.alpha = 0.5f
        binding.switchToSingUp.setOnClickListener {
            val bundle = bundleOf("email" to binding.emailText.text.toString())
            findNavController().navigate(R.id.action_singInFragment_to_singUpFragment, bundle)

        }
        binding.passText.doAfterTextChanged {
            checkValidations()
        }
        binding.emailText.doAfterTextChanged {
            checkValidations()
        }

        binding.singInButton.setOnClickListener {
            signInToFirebase(binding.emailText.text.toString(), binding.passText.text.toString())
        }

        binding.forgetPassword.setOnClickListener {
            val bundle = bundleOf("email" to binding.emailText.text.toString())

            findNavController().navigate(R.id.action_singInFragment_to_resetPasswordFragment,bundle)
        }
    }

    private fun checkValidations() {
        if (checkEmail() && checkPassword()) {
            binding.singInButton.isClickable = true
            binding.singInButton.alpha = 1f
        } else {
            binding.singInButton.isClickable = false
            binding.singInButton.alpha = 0.5f
        }
    }

    private fun checkPassword(): Boolean {

        if (binding.passText.text!!.length < 6) {
            binding.passwordLayout.helperText = ("Please enter minimum 6 characters")
            return false
        }
        binding.passwordLayout.helperText = null
        return true
    }

    private fun checkEmail(): Boolean {

        if (emailValidator(binding.emailText)) {
            binding.emailLayout.helperText = ("Please enter a correct email address")
            return false
        }
        binding.emailLayout.helperText = null
        return true


    }

    private fun emailValidator(etMail: EditText): Boolean {

        val emailToText = etMail.text.toString()

        return !(emailToText.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText)
            .matches())
    }

    private fun signInToFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(ContentValues.TAG, "singInUserWithEmail:success")
                    val user = auth.currentUser
                    findNavController().navigate(R.id.action_singInFragment_to_mainFragment)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "signInUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        requireContext(),
                        task.exception?.message.toString(),
                        Toast.LENGTH_LONG,
                    ).show()

                }
            }
    }
}