package com.example.moviecatch.ui.fragments.auth

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.moviecatch.R
import com.example.moviecatch.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SingUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignUpBinding.inflate(layoutInflater)
        val view = binding.root
        // Initialize Firebase Auth
        auth = Firebase.auth

        binding.emailText.setText(arguments?.getString("email"))
        buttonsHandle()
        return view
    }

    private fun buttonsHandle() {

        binding.signUpButton.isClickable = false
        binding.signUpButton.alpha = 0.5f

        binding.switchToSingIn.setOnClickListener {
            val bundle = bundleOf("email" to binding.emailText.text.toString())

            findNavController().navigate(R.id.action_singUpFragment_to_singInFragment, bundle)
        }

        binding.emailText.doAfterTextChanged {
            checkValidations()
        }
        binding.passText.doAfterTextChanged {
            checkValidations()
        }
        binding.confirmPassText.doAfterTextChanged {
            checkValidations()
        }

        binding.signUpButton.setOnClickListener {
            singUpToFirebase(binding.emailText.text.toString(), binding.passText.text.toString())
        }
    }

    private fun checkValidations() {
        if (checkEmail() && checkPassword()) {
            binding.signUpButton.isClickable = true
            binding.signUpButton.alpha = 1f
        } else {
            binding.signUpButton.isClickable = false
            binding.signUpButton.alpha = 0.5f
        }
    }

    private fun checkPassword(): Boolean {

        if (binding.passText.text!!.length < 6) {
            binding.passwordLayout.helperText = ("Please enter minimum 6 characters")
            return false
        }
        binding.passwordLayout.helperText = null
        if (binding.passText.text.toString() != binding.confirmPassText.text.toString()) {
            binding.confirmPasswordLayout.helperText = ("Passwords must be the same")
            return false
        }
        binding.confirmPasswordLayout.helperText = null
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun singUpToFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(
                        requireContext(),
                        "Account Created Successfully",
                        Toast.LENGTH_LONG,
                    ).show()
                    val bundle = bundleOf("email" to email)
                    Handler(Looper.getMainLooper()).postDelayed({
                        findNavController().navigate(
                            R.id.action_singUpFragment_to_singInFragment,
                            bundle
                        )
                    }, 1000)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        requireContext(),
                        task.exception?.message.toString(),
                        Toast.LENGTH_LONG,
                    ).show()

                }
            }
    }
}