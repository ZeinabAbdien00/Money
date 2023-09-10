package com.iti.moneyapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.iti.moneyapp.data.datastore.DataStoreImplementation

class MyApplication : Application() {

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        @SuppressLint("StaticFieldLeak")
        lateinit var instance: MyApplication
            private set

        lateinit var dataStore: DataStoreImplementation

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        instance = this
        dataStore = DataStoreImplementation(context)
    }

}