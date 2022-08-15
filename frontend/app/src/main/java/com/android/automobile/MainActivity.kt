package com.android.automobile

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.android.automobile.data.repository.UserAuthentication
import com.android.automobile.view.util.startAuthActivity
import com.android.automobile.view.util.startHomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var user: UserAuthentication

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
