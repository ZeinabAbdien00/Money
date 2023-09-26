package com.iti.moneyapp.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.findNavController
import com.iti.moneyapp.R
import com.iti.moneyapp.adapter.home.TapViewPagerAdapter
import com.iti.moneyapp.databinding.ActivityHomeBinding
import np.com.susanthapa.curved_bottom_navigation.CbnMenuItem

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private lateinit var adapter: TapViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
//
//        adapter = TapViewPagerAdapter(supportFragmentManager, lifecycle)
//
//        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Income"))
//        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Expense"))
//
//        binding.viewPager.adapter = adapter

//        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//                if (tab != null)
//                    binding.viewPager.currentItem = tab.position
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//
//
//            }
//        })

//        binding.viewPager.registerOnPageChangeCallback(object :
//            ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
//            }
//        })

//        setBottomNavigationInNormalWay()
//
//        binding.bottomNavigation.setOnClickMenuListener {
//            if(it.id == ID_HOME)
//                setFindNavController(R.id.loginFragment)
//            if(it.id == ID_ACCOUNT)
//                setFindNavController(R.id.startFragment)
//            if(it.id == ID_EXPLORE)
//                setFindNavController(R.id.loginFragment)
//            if(it.id == ID_MESSAGE)
//                setFindNavController(R.id.signUpFragment)
//            if(it.id == ID_NOTIFICATION)
//                setFindNavController(R.id.signUpFragment)
//        }


        val menuItems = arrayOf(
            CbnMenuItem(
                R.drawable.ic_home, // the icon
                R.drawable.avd_home, // the AVD that will be shown in FAB
                R.id.dashboardFragment // optional if you use Jetpack Navigation
            ),
            CbnMenuItem(
                R.drawable.ic_add,
                R.drawable.avd_add,
                R.id.addFragment
            ),
            CbnMenuItem(
                R.drawable.ic_settings,
                R.drawable.avd_setting,
                R.id.settingFragment
            )
        )
        binding.navView.setMenuItems(menuItems, 0)

        binding.navView.setOnMenuItemClickListener { cbnMenuItem, i ->
            if (i == 0) setFindNavController(R.id.dashboardFragment)
            if (i == 1) setFindNavController(R.id.addFragment)
            if (i == 2) setFindNavController(R.id.settingFragment)

        }
    }

    private fun setFindNavController(host: Int) {
        val navInflater = findNavController(R.id.nav_host_fragment_home).navInflater
        val graph: NavGraph = navInflater.inflate(R.navigation.home_nav)
        graph.setStartDestination(host)
        val navController: NavController = findNavController(R.id.nav_host_fragment_home)
        navController.graph = graph

    }


    companion object {
        private const val ID_HOME = 0
        private const val ID_EXPLORE = 1
        private const val ID_MESSAGE = 2
        private const val ID_NOTIFICATION = 3
        private const val ID_ACCOUNT = 4
    }

//    private fun setBottomNavigationInNormalWay() {
//        binding.bottomNavigation.apply {
//            setSelectedIndex(0)
//            add(
//                Model(
//                    icon = R.drawable.ic_lock,
//                    destinationId = R.id.loginFragment,
//                    id = ID_HOME,
//                    text = R.string.login,
//                    count = R.string.login
//                )
//            )
//            add(
//                Model(
//                    icon = R.drawable.ic_phone,
//                    destinationId = R.id.signUpFragment,
//                    id = ID_NOTIFICATION,
//                    text = R.string.sign_up,
//                    count = R.string.sign_up
//                )
//            )
//            add(
//                Model(
//                    icon = R.drawable.ic_gmail,
//                    destinationId = R.id.startFragment,
//                    id = ID_ACCOUNT,
//                    text = R.string.get_started,
//                    count = R.string.get_started
//                )
//            )
//            add(
//                Model(
//                    icon = R.drawable.ic_facebook,
//                    destinationId = R.id.loginFragment,
//                    id = ID_EXPLORE,
//                    text = R.string.login,
//                    count = R.string.login
//                )
//            )
//            add(
//                Model(
//                    icon = R.drawable.ic_home,
//                    destinationId = R.id.signUpFragment,
//                    id = ID_MESSAGE,
//                    text = R.string.sign_up,
//                    count = R.string.sign_up
//                )
//            )
//        }
//    }
}