package com.iti.moneyapp.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.iti.moneyapp.model.auth.AuthModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext


private val Context.dataStore by preferencesDataStore("user_data")

class DataStoreImplementation(
    appContext: Context?,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : DataStore {

    private val dataStoreO by lazy {
        appContext?.dataStore
    }

    companion object {
        const val USER_NAME = "userName"
        const val USER_PHONE = "userPhone"
        const val USER_EMAIL = "userEmail"
        const val USER_ID = "userId"
        const val USER_IMAGE_URL = "userImage"
        const val IS_LOGGED_IN = "loggedIn"
        const val IS_PASSED_ON_BOARDING = "passedIntro"
        const val USER_PASSWORD = "savePassword"

    }

    override suspend fun isLoggedIn(): Boolean {
        return withContext(dispatcher) {
            dataStoreO?.data?.map { settings ->
                settings[booleanPreferencesKey(IS_LOGGED_IN)]
            }?.first() ?: false
        }
    }

    override suspend fun clearAllData() {
        dataStoreO?.edit { it.clear() }
        setPassedOnBoarding(true)
    }

    override suspend fun setUSerLogged(isLogged: Boolean) {
        withContext(dispatcher) {
            dataStoreO!!.edit { settings ->
                settings[booleanPreferencesKey(IS_LOGGED_IN)] = isLogged
            }
        }
    }

    override suspend fun setLogged(isLogged: Boolean) {
        withContext(dispatcher) {
            dataStoreO?.edit { settings ->
                settings[booleanPreferencesKey(IS_LOGGED_IN)] = isLogged
            }
        }
    }

    override suspend fun saveUser(saveData: AuthModel) {
//        val name = saveData.fullName ?: "No Name"
//        val email = saveData.email ?: "No email"
//        val phone = saveData.phone ?: "No phone"
//        val password = saveData.password ?: "No password"
//        val imageUri = saveData.imgUri ?: ""
//        val userId = saveData.userId ?: ""
//
//        dataStoreO?.edit { settings ->
//            settings[stringPreferencesKey(USER_NAME)] = name
//            settings[stringPreferencesKey(USER_EMAIL)] = email
//            settings[stringPreferencesKey(USER_PHONE)] = phone
//            settings[stringPreferencesKey(USER_PASSWORD)] = password
//            settings[stringPreferencesKey(USER_IMAGE_URL)] = imageUri
//            settings[stringPreferencesKey(USER_ID)] = userId
//        }
    }

    override suspend fun isPassedOnBoarding(): Boolean = withContext(dispatcher) {
        dataStoreO!!.data.map { settings ->
            settings[booleanPreferencesKey(IS_PASSED_ON_BOARDING)] ?: false
        }.first()
    }

    override suspend fun setPassedOnBoarding(isPassed: Boolean) {
        withContext(dispatcher) {
            dataStoreO!!.edit { settings ->
                settings[booleanPreferencesKey(IS_PASSED_ON_BOARDING)] = isPassed
            }
        }
    }

}