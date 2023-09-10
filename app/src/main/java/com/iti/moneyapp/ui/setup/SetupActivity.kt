package com.iti.moneyapp.ui.setup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.iti.moneyapp.MyApplication.Companion.dataStore
import com.iti.moneyapp.R
import com.iti.moneyapp.data.datastore.DataStoreImplementation
import com.iti.moneyapp.databinding.ActivitySetupBinding
import com.iti.moneyapp.ui.home.HomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SetupActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySetupBinding
    private lateinit var splashScreen: SplashScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetupBinding.inflate(layoutInflater)
        splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { true }
        handleDirections()
        setContentView(binding.root)
    }

    private fun handleDirections() {
        GlobalScope.launch(Dispatchers.Main) {
            if (dataStore.isLoggedIn()) {
                startActivity(Intent(this@SetupActivity, HomeActivity::class.java))
                splashScreen.setKeepOnScreenCondition { false }
                this@SetupActivity.finish()
            }else{
                splashScreen.setKeepOnScreenCondition { false }

            }
//            if (dataStore.isPassedOnBoarding()) {
//                setFindNavController(R.id.loginFragment)
//                splashScreen.setKeepOnScreenCondition { false }
//
//            } else {
//                setFindNavController(R.id.onBoardingFragment)
//                splashScreen.setKeepOnScreenCondition { false }
//
//            }
        }
    }

}