package com.iti.moneyapp.ui.setup

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.iti.moneyapp.MyApplication
import com.iti.moneyapp.data.datastore.DataStoreImplementation
import com.iti.moneyapp.model.auth.AuthModel
import kotlinx.coroutines.Dispatchers

class SetupRepository {

    private fun getAuthFirebase() = FirebaseAuth.getInstance()

    fun signWithEmail(email: String, password: String) =
        getAuthFirebase().createUserWithEmailAndPassword(email, password).isSuccessful


    fun loginWithEmail(email: String, password: String) =
        getAuthFirebase().signInWithEmailAndPassword(email, password)

    fun forgotPassword(email: String) {
        getAuthFirebase().sendPasswordResetEmail(email)
    }

    suspend fun saveUserLogin(user: AuthModel, context: Context?){
        MyApplication.dataStore = DataStoreImplementation(appContext = context, Dispatchers.IO)
        MyApplication.dataStore.setLogged(true)
        MyApplication.dataStore.setUSerLogged(true)
        MyApplication.dataStore.saveUser(user)

    }
}

