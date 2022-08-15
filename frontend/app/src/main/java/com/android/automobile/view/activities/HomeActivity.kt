package com.android.automobile.view.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.android.automobile.R
import com.android.automobile.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            navView.background = null
            coordinator.background = null
            navView.menu.getItem(2).isEnabled = false
            val navView: BottomNavigationView = navView
            val navController = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
            navView.setupWithNavController(navController.navController)

            floatingActionButton.setOnClickListener { view ->
                if (navController.findNavController().currentDestination != null) {
//                    navController.findNavController().navigate(R.id.googleMapsNavigationFragment)
                    routUser("4.908538200000001,-1.7563672","4.989325299999999,-1.7562893")
                } else {
                    Timber.d("NAVS", "DESTINATION NOT FOUND")
                }
            }
        }
    }


    private fun routUser(source:String, destination:String){
        //check if map not installed
        try {
            //when map is installed, initialize uri
            val uri = Uri.parse("https://www.google.co.in/maps/dir/$source/$destination")

            val launchMap = Intent(Intent.ACTION_VIEW, uri)
            launchMap.setPackage("com.google.android.apps.maps")
            launchMap.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(launchMap)


        }catch (e: ActivityNotFoundException){
            val uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps")
            val goToPlayStoreIntent = Intent(Intent.ACTION_VIEW, uri)
            goToPlayStoreIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(goToPlayStoreIntent)
        }
    }
}