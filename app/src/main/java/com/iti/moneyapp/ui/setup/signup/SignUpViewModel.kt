package com.iti.moneyapp.ui.setup.signup

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iti.moneyapp.model.auth.AuthModel
import com.iti.moneyapp.ui.setup.SetupRepository
import com.iti.moneyapp.utils.validation.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class SignUpViewModel : ViewModel() {

    private val setupRepository = SetupRepository()

    private val _email = MutableLiveData<EmailError?>()
    val email: LiveData<EmailError?> = _email

    private val _password = MutableLiveData<PasswordError?>()
    val password: LiveData<PasswordError?> = _password

    private val _fullName = MutableLiveData<FullNameError?>()
    val fullName: LiveData<FullNameError?> = _fullName

    private val _phoneNumber = MutableLiveData<PhoneError?>()
    val phoneNumber: LiveData<PhoneError?> = _phoneNumber

    private val _confirmPassword = MutableLiveData<ConfirmPasswordError?>()
    val confirmPassword: LiveData<ConfirmPasswordError?> = _confirmPassword

    private val _errorSignup = MutableLiveData<String>()
    val errorSignup: LiveData<String> = _errorSignup

    fun isValidSignUp(
        email: String,
        fullName: String,
        phone: String,
        password: String,
        confirmPassword: String,
    ): Boolean {

        val validationEmail = validateEmail(email = email)
        val validationFullName = validateFullName(fullName = fullName)
        val validationPhone = validatePhoneNumber(phoneNumber = phone)
        val validationPassword = validatePassword(password = password)
        val validationConfirmPassword =
            validateConfirmPassword(confirmPassword = confirmPassword, password = password)

        val isValidateEmail: Boolean = when (validationEmail) {
            is ValidationResult.Error -> {
                _email.value = validationEmail.type
                false
            }
            is ValidationResult.Success -> {
                _email.value = null
                true
            }

        }

        val isValidationFullName: Boolean = when (validationFullName) {
            is ValidationResult.Error -> {
                _fullName.value = validationFullName.type
                false
            }
            is ValidationResult.Success -> {
                _fullName.value = null
                true
            }

        }

        val isValidationPhone: Boolean = when (validationPhone) {
            is ValidationResult.Error -> {
                _phoneNumber.value = validationPhone.type
                false
            }
            is ValidationResult.Success -> {
                _phoneNumber.value = null

                true
            }

        }

        val isValidationPassword: Boolean = when (validationPassword) {
            is ValidationResult.Error -> {
                _password.value = validationPassword.type
                false
            }
            is ValidationResult.Success -> {
                _password.value = null
                true
            }

        }

        val isValidationConfirmPassword: Boolean = when (validationConfirmPassword) {
            is ValidationResult.Error -> {
                _confirmPassword.value = validationConfirmPassword.type
                false
            }
            is ValidationResult.Success -> {
                _confirmPassword.value = null
                true
            }

        }

        return isValidateEmail &&
                isValidationFullName &&
                isValidationPassword &&
                isValidationPhone &&
                isValidationConfirmPassword

    }

    fun signIn(email: String, password: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = setupRepository.signWithEmail(email, password)
            try {
                response.await()
                _errorSignup.value = "true"
            } catch (e: Exception) {
                _errorSignup.value = e.toString()
            }
        }
    }

    fun resetData() {
        _fullName.value = null
        _email.value = null
        _password.value = null
        _confirmPassword.value = null
        _phoneNumber.value = null
    }

    fun userId() = setupRepository.userId()

    suspend fun saveUserDataAndLogFlag(user: AuthModel, context: Context?) {
        setupRepository.saveUserLogin(user, context)
    }

}