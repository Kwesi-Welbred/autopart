package com.android.automobile.viewmodel.products

import android.view.View
import androidx.lifecycle.ViewModel
import com.android.automobile.data.repository.UserRepository
import com.android.automobile.view.util.startAuthActivity
import com.android.automobile.view.util.startNewActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    private val repository: UserRepository
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
