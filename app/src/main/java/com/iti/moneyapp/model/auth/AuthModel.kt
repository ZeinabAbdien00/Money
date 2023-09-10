package com.iti.moneyapp.model.auth

data class AuthModel(
    val userId: String? = "",
    val fullName: String? = "",
    val email: String? = "",
    val phone: String? = "",
    val password: String? = "",
    val imgUri: String? = ""
)