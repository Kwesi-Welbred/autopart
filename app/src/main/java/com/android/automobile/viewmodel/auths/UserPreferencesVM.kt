package com.android.automobile.viewmodel.auths

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.automobile.data.source.Credentials
import com.android.automobile.data.source.UserPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserPreferencesVM(private val userPreferences: UserPreferences) : ViewModel() {


    var email: MutableLiveData<String> = MutableLiveData("")
    var password: MutableLiveData<String> = MutableLiveData("")
    private var credentials: MutableLiveData<Credentials> = MutableLiveData()


    fun saveCredentials() {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferences.saveCredentials(
                Credentials(
                    email = email.value ?: "",
                    password = password.value ?: ""
                )
            )
        }
    }


    fun getCredentials() {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferences.getCredentials().collect {
                credentials.postValue(it)
            }
        }
    }

    val clearCredentials = viewModelScope.launch {
        userPreferences.clearCredentials()
    }
}