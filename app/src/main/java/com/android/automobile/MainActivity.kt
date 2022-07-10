package com.android.automobile

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.android.automobile.data.repository.UserRepository
import com.android.automobile.view.fragments.LoginFragment.Companion.TAG
import com.android.automobile.view.util.startAuthActivity
import com.android.automobile.view.util.startHomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var user: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            delay(3000)
               if (user.getCurrentUser == null) startAuthActivity() else startHomeActivity()
               Log.d("USER", user.getCurrentUser.toString())
        }
    }
}
