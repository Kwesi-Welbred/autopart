package com.android.automobile.viewmodel.auths

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.automobile.data.repository.UserRepository
import com.android.automobile.model.TYPE
import com.android.automobile.model.User
import com.android.automobile.view.util.NetworkManager
import com.android.automobile.view.util.Resource
import com.android.automobile.view.util.UtilityMethods
import com.google.android.gms.tasks.RuntimeExecutionException
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: UserRepository,
    private val networkControl: NetworkManager,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val userLiveData = MutableLiveData<Resource<User>>()
    private val _saveUserLiveData = MutableLiveData<Resource<User>>()
    val saveUserLiveData = _saveUserLiveData

    fun signUpUser(fullName:String, email: String, password: String): LiveData<Resource<User>> {
        when {
            TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && TextUtils.isEmpty(fullName) -> {
                userLiveData.postValue(Resource.error(null, "Field must not be empty"))
            }
            password.length <= 8 -> {
                userLiveData.postValue(Resource.error(null, "Password must not be less than 8"))
            }
            UtilityMethods.emailValidator(email)->{
                userLiveData.postValue(Resource.error(null,"You entered incorrect password"))
            }
            networkControl.isConnected() -> {
                userLiveData.postValue(Resource.loading(null))
                firebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener {
                    try {
                         if (it.result?.signInMethods?.size == 0) {
                        repository.signUpUser(fullName, email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    firebaseAuth.currentUser?.sendEmailVerification()
                                    userLiveData.postValue(
                                        Resource.success(
                                            User(
                                                uid = firebaseAuth.uid,
                                                fullName = fullName,
                                                email,
                                                password
                                            )
                                        )
                                    )
                                } else {
                                    userLiveData.postValue(
                                        Resource.error(
                                            null,
                                            it.exception?.message.toString()
                                        )
                                    )
                                }
                            }
                    } else {
                        userLiveData.postValue(Resource.error(null, "email already exist"))
                    }
                    }catch (e: RuntimeExecutionException){
                        userLiveData.postValue(Resource.error(null, e.message.toString()))
                    }catch (e:FirebaseNetworkException){
                         userLiveData.postValue(Resource.error(null, e.message.toString()))
                    }catch (e:FirebaseException){
                         userLiveData.postValue(Resource.error(null, e.message.toString()))
                    }
                }
            }
            else -> {
                userLiveData.postValue(Resource.error(null, "No internet connection"))
            }
        }
        return userLiveData
    }

    fun saveUser(fullName: String, email: String, password: String) {
        repository.saveUser(fullName,email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                _saveUserLiveData.postValue(Resource.success(User(uid = firebaseAuth.uid, fullName = fullName, email,password, type = TYPE.ADMIN)))
            } else {
                _saveUserLiveData.postValue(Resource.error(null, it.exception?.message.toString()))
            }
        }
    }

    fun emailVerification(email: String) = viewModelScope.launch {
        repository.emailVerification
    }
}