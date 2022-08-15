package com.android.automobile.viewmodel.products

import android.view.View
import androidx.lifecycle.ViewModel
import com.android.automobile.data.repository.UserAuthentication
import com.android.automobile.view.util.startAuthActivity
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    private val repository: UserAuthentication
) : ViewModel() {

    val user by lazy {
        repository.getCurrentUser
    }

     val userCollectionRef = repository.userCollection("Users")
    fun logout(view: View){
        repository.singOutUser
        view.context.startAuthActivity()
    }
}
