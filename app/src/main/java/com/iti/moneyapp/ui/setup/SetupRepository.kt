package com.iti.moneyapp.ui.setup

import com.google.firebase.auth.FirebaseAuth

class SetupRepository {

    private fun getAuthFirebase() = FirebaseAuth.getInstance()

    fun signWithEmail(email: String, password: String) =
        getAuthFirebase().createUserWithEmailAndPassword(email, password).isSuccessful


    fun loginWithEmail(email: String, password: String) =
        getAuthFirebase().signInWithEmailAndPassword(email, password)

    fun forgotPassword(email: String) {
        getAuthFirebase().sendPasswordResetEmail(email)
    }
}

