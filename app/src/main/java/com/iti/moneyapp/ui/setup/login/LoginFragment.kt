package com.iti.moneyapp.ui.setup.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import com.iti.moneyapp.databinding.FragmentLoginBinding
import com.iti.moneyapp.utils.Constants.Companion.accountNotFound
import com.iti.moneyapp.utils.Constants.Companion.passwordLoginError
import com.iti.moneyapp.utils.hideKeypad

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var email: String
    private lateinit var password: String
    private var isValidLoginData: Boolean = false
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListeners()
        observePass()
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
                forgetPassword()
            }
        }
    }

    private fun forgetPassword() {
        setUserData()
        if (email.isNotEmpty()) {
            if (viewModel.isValidLogin(email = email, password = "S")) {
                viewModel.forgotPassword(email)
                Toast.makeText(requireContext(), "Check your email", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Invalid email", Toast.LENGTH_SHORT).show()
            }
        } else
            Toast.makeText(requireContext(), "Enter your email", Toast.LENGTH_SHORT).show()
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
                viewModel.login(email, password)
                observePass()
            } else if (viewModel.email.value.toString().isNotEmpty()) {
                Toast.makeText(context, "Invalid email", Toast.LENGTH_LONG).show()
            } else if (viewModel.password.value.toString().isNotEmpty()) {
                Toast.makeText(context, "Invalid password", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(context, "Email or Password field is empty", Toast.LENGTH_LONG).show()
        }
    }

    private fun observePass() {
        viewModel.errorLogin.observe(viewLifecycleOwner) {
            when (it) {
                passwordLoginError -> {
                    Log.d("LOG_TAG", "invalid")
                    toast("The password is invalid")
                }
                accountNotFound -> {
                    Log.d("LOG_TAG", "No")
                    toast("No Account use this Email")
                }
                "true" -> {
                    toast("Account Existed")
                }
            }
        }
    }

    private fun toast(msg: String){
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}