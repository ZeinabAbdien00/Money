package com.iti.moneyapp.utils

class Constants {

    companion object {
        const val passwordLoginError =
            "com.google.firebase.auth.FirebaseAuthInvalidCredentialsException: The password is invalid or the user does not have a password."

        const val accountNotFound =
            "com.google.firebase.auth.FirebaseAuthInvalidUserException: There is no user record corresponding to this identifier. The user may have been deleted."
    }
}