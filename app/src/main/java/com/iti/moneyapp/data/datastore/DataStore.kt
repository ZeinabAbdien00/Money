package com.iti.moneyapp.data.datastore

import com.iti.moneyapp.model.auth.AuthModel

interface DataStore {

    suspend fun clearAllData()

    suspend fun isLoggedIn(): Boolean

    suspend fun setUSerLogged(isLogged: Boolean)

    suspend fun setLogged(isLogged: Boolean)

    suspend fun saveUser(saveData: AuthModel)

    suspend fun isPassedOnBoarding(): Boolean

    suspend fun setPassedOnBoarding(isPassed: Boolean)

}