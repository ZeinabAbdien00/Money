package com.iti.moneyapp.ui.setup.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.iti.moneyapp.databinding.FragmentSignUpBinding
import com.iti.moneyapp.model.auth.AuthModel
import com.iti.moneyapp.ui.home.HomeActivity
import com.iti.moneyapp.utils.Constants
import com.iti.moneyapp.utils.hideKeypad
import com.iti.moneyapp.utils.validation.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModels()
    private lateinit var fullName: String
    private lateinit var email: String
    private lateinit var phoneNumber: String
    private lateinit var password: String
    private lateinit var confirmPassword: String
    private lateinit var error: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        observation()
    }

    private fun observation() {
        viewModel.email.observe(viewLifecycleOwner) { emailError ->
            if (!emailError.toString().equals(null))
                error = emailError.toString()
        }

        viewModel.fullName.observe(viewLifecycleOwner) { fullNameError ->
            if (!fullNameError.toString().equals(null))
                error = fullNameError.toString()

        }
    }

    private fun setOnClickListeners() {
        binding.apply {
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }
            btnSignup.setOnClickListener {
                requireActivity().hideKeypad()
                userData()
                val isValidSignUpData = viewModel.isValidSignUp(
                    email = email,
                    fullName = fullName,
                    phone = phoneNumber,
                    password = password,
                    confirmPassword = confirmPassword
                )
                if (isValidSignUpData) {
                    observeSignUp()
                } else {
                    showError()
                    viewModel.resetData()
                }
            }
        }
    }

    private fun observeSignUp() {
        viewModel.errorSignup.observe(viewLifecycleOwner) {
            when (it) {
                "true" -> {
                    val userData = AuthModel(
                        userId = viewModel.userId(),
                        fullName = fullName,
                        email = email,
                        phone = phoneNumber,
                        password = password,
                        imgUri = ""
                    )
                    saveDataStore(userData)
                    CoroutineScope(Dispatchers.IO).launch {
                        startActivity(Intent(requireActivity(), HomeActivity::class.java))
                        requireActivity().finish()
                    }
                }
                else -> Toast.makeText(
                    requireContext(),
                    it.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    private fun userData() {
        binding.apply {
            fullName = etName.text.toString()
            email = etEmail.text.toString()
            phoneNumber = etPhone.text.toString()
            password = etPassword.text.toString()
            confirmPassword = etConfirmPassword.text.toString()
        }
    }

    private fun showError() {

        if (viewModel.fullName.value.toString() != "null")
            Toast.makeText(
                context,
                fullNameError(viewModel.fullName.value),
                Toast.LENGTH_LONG
            ).show()
        else if (viewModel.email.value.toString() != "null")
            Toast.makeText(
                context,
                emailError(viewModel.email.value),
                Toast.LENGTH_LONG
            ).show()
        else if (viewModel.phoneNumber.value.toString() != "null")
            Toast.makeText(
                context,
                phoneNumber(viewModel.phoneNumber.value),
                Toast.LENGTH_LONG
            ).show()
        else if (viewModel.password.value.toString() != "null")
            Toast.makeText(
                context,
                passwordError(viewModel.password.value),
                Toast.LENGTH_LONG
            ).show()
        else if (viewModel.confirmPassword.value.toString() != "null")
            Toast.makeText(
                context,
                confirmPasswordError(viewModel.confirmPassword.value),
                Toast.LENGTH_LONG
            ).show()

    }

    private fun phoneNumber(phoneError: PhoneError?): String {
        return when (phoneError) {
            PhoneError.INVALID -> "Invalid phone number"
            else -> "Phone number field is empty"
        }
    }

    private fun emailError(emailError: EmailError?): String {
        return when (emailError) {
            EmailError.INVALID -> "Invalid Email"
            else -> "Email field is empty"
        }

    }

    private fun passwordError(passwordError: PasswordError?): String {
        return when (passwordError) {
            PasswordError.IS_LONG -> "Password is too long"
            PasswordError.IS_SHORT -> "Password is too short"
            else -> "Password field is empty"
        }
    }

    private fun confirmPasswordError(confirmPasswordError: ConfirmPasswordError?): String {
        return when (confirmPasswordError) {
            ConfirmPasswordError.NOT_MATCHING_PASSWORD -> "Confirm password doesn't match"
            else -> "Confirm password field is empty"
        }
    }

    private fun fullNameError(fullNameError: FullNameError?): String {
        return when (fullNameError) {
            FullNameError.INVALID -> "Invalid name"
            FullNameError.IS_SHORT -> "Name is short"
            FullNameError.IS_LONG -> "Name is long"
            else -> "Full name field is empty"
        }
    }

    private fun saveDataStore(data: AuthModel) {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.saveUserDataAndLogFlag(data, context = context)
        }
    }
}