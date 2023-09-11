package com.iti.moneyapp.ui.setup.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iti.moneyapp.MyApplication.Companion.dataStore
import com.iti.moneyapp.data.datastore.DataStoreImplementation
import com.iti.moneyapp.model.auth.AuthModel
import com.iti.moneyapp.ui.setup.SetupRepository
import com.iti.moneyapp.utils.validation.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {

    private val setupRepository = SetupRepository()

    private val _email = MutableLiveData<EmailError?>()
    val email: LiveData<EmailError?> = _email

    private val _password = MutableLiveData<LoginPasswordError?>()
    val password: LiveData<LoginPasswordError?> = _password

    private val _errorLogin = MutableLiveData<String>()
    val errorLogin: LiveData<String> = _errorLogin


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

    fun login(email: String, password: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = setupRepository.loginWithEmail(email, password)
            try {
                response.await()
                _errorLogin.value = "true"
            } catch (e: Exception) {
                _errorLogin.value = e.toString()
            }
        }
    }

    fun forgotPassword(email: String) = setupRepository.forgotPassword(email)

    suspend fun saveUserDataAndLogFlag(user: AuthModel, context: Context?) {
        setupRepository.saveUserLogin(user, context)
    }

}