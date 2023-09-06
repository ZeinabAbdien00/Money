package com.iti.moneyapp.ui.setup.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iti.moneyapp.ui.setup.SetupRepository
import com.iti.moneyapp.utils.validation.*

class LoginViewModel : ViewModel() {

    private val setupRepository = SetupRepository()

    private val _email = MutableLiveData<EmailError?>()
    val email: LiveData<EmailError?> = _email

    private val _password = MutableLiveData<LoginPasswordError?>()
    val password: LiveData<LoginPasswordError?> = _password

    fun isValidLogin(email: String, password: String): Boolean {

        val validationEmail = validateEmail(email = email)
        val validationPassword = validateLoginPassword(password = password)
        val isValidEmail: Boolean
        val isValidPassword: Boolean

        when (validationEmail) {
            is ValidationResult.Error -> {
                _email.value = validationEmail.type
                isValidEmail = false
            }
            is ValidationResult.Success -> {
                _email.value = null
                isValidEmail = true
            }
        }

        when (validationPassword) {
            is ValidationResult.Error -> {
                _password.value = validationPassword.type
                isValidPassword = false
            }
            is ValidationResult.Success -> {
                _password.value = null
                isValidPassword = true
            }
        }
        return isValidEmail && isValidPassword
    }

    fun login(email: String, password: String) = setupRepository.loginWithEmail(email, password)

    fun forgotPassword(email: String) = setupRepository.forgotPassword(email)

}