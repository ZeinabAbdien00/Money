package com.iti.moneyapp.ui.setup.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.iti.moneyapp.databinding.FragmentLoginBinding
import com.iti.moneyapp.utils.hideKeypad
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String
    private var isValidLoginData: Boolean = false
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)

        auth = FirebaseAuth.getInstance()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()

    }

    private fun setOnClickListeners() {
        binding.apply {
            btnLogin.setOnClickListener {
                requireActivity().hideKeypad()
                login()
            }
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
            tvForgotPassword.setOnClickListener {

                if (etEmail.text.toString().isNotEmpty()) {
                    auth.sendPasswordResetEmail(etEmail.text.toString())
                    Toast.makeText(requireContext(), "Check your email", Toast.LENGTH_SHORT).show()
                } else
                    Toast.makeText(requireContext(), "Enter your email", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUserData() {
        email = binding.etEmail.text.toString()
        password = binding.etPassword.text.toString()
    }

    private fun login() {
        setUserData()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            isValidLoginData = viewModel.isValidLogin(email = email, password = password)
            if (isValidLoginData) {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        auth.signInWithEmailAndPassword(email, password).await()

                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "No Account \n $e", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } else if (viewModel.email.value.toString().isNotEmpty()) {
                Toast.makeText(context, "Invalid email", Toast.LENGTH_LONG).show()
            } else if (viewModel.password.value.toString().isNotEmpty()) {
                Toast.makeText(context, "Invalid password", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(context, "Email or Password field is empty", Toast.LENGTH_LONG).show()
        }
    }

}