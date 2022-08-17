package com.android.automobile.viewmodel.auths

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.*
import com.android.automobile.data.repository.GetUserData
import com.android.automobile.data.repository.UserAuthentication
import com.android.automobile.model.User
import com.welbtech.autopart.view.fragments.LoginFragment.Companion.TAG
import com.android.automobile.view.util.NetworkManager
import com.android.automobile.view.util.Resource
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.RuntimeExecutionException
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: UserAuthentication,
    private val networkControl: NetworkManager,
    private val firebaseAuth: FirebaseAuth,
    private val userData: GetUserData
) : ViewModel() {

    private val userLiveData = MutableLiveData<Resource<User>>()
    private val gMailUserLiveData = MutableLiveData<Resource<User>>()
    private val sendResetPasswordLiveData = MutableLiveData<Resource<User>>()

    private val observerUserData = MutableLiveData<Resource<User>>()

    fun signInUser(email: String, password: String): LiveData<Resource<User>> {
        when {
            TextUtils.isEmpty(email) && TextUtils.isEmpty(password) -> {
                userLiveData.postValue(Resource.error(null, "Enter email and password"))
            }
            networkControl.isConnected() -> {
                try {
                    userLiveData.postValue(Resource.loading(null))
                    firebaseAuth.fetchSignInMethodsForEmail(email).addOnCompleteListener {
                        //check if email exists
                        try {
                            if (it.result?.signInMethods?.size == 0) {
                                userLiveData.postValue(Resource.error(null, "Email does not exist"))
                            } else {
                                repository.signInUser(email, password)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            firebaseAuth.currentUser?.isEmailVerified?.let { verified ->
                                                Log.d(TAG, ":::::::::::::signInWithEmail:success")
                                                if (verified) {
                                                    repository.searchUser.addOnCompleteListener { userTask ->
                                                        if (userTask.isSuccessful) {
                                                            userTask.result?.documents?.forEach { snapshot ->
                                                                if (snapshot.data!!["email"] == email) {
                                                                    val name = snapshot.data?.getValue("fullName")
                                                                    userLiveData.postValue(
                                                                        Resource.success(
                                                                            User(
                                                                                email=firebaseAuth.currentUser?.email!!,
                                                                                fullName = name?.toString()!!
                                                                            )
                                                                        )
                                                                    )
                                                                }
                                                            }
                                                        } else {
                                                            userLiveData.postValue(
                                                                Resource.error(
                                                                    null,
                                                                    userTask.exception?.message.toString()
                                                                )
                                                            )
                                                        }
                                                    }
                                                } else {
                                                    userLiveData.postValue(
                                                        Resource.error(
                                                            null,
                                                            "Email is not verified, Please check your inbox"
                                                        )
                                                    )
                                                }
                                            }
                                        } else {
                                            userLiveData.postValue(
                                                Resource.error(
                                                    null,
                                                    task.exception?.message.toString()
                                                )
                                            )
                                            Timber.e(task.exception.toString())
                                        }
                                    }
                            }
                        } catch (e: RuntimeExecutionException) { userLiveData.postValue(Resource.error(null, e.message.toString()))}
                    }
                } catch (e: RuntimeExecutionException) {
                    userLiveData.postValue(Resource.error(null, e.message.toString()))
                }
            }else -> {
                userLiveData.postValue(Resource.error(null, "No internet connection"))
            }
        }
        return userLiveData
    }

    fun signInWithGoogle(acct: GoogleSignInAccount): LiveData<Resource<User>> {
        repository.signInWithGoogle(acct).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                gMailUserLiveData.postValue(
                    Resource.success(
                        User(
                            email = firebaseAuth.currentUser?.email!!,
                            fullName = firebaseAuth.currentUser?.displayName!!
                        )
                    )
                )
            } else {
                gMailUserLiveData.postValue(Resource.error(null, "couldn't sign in user"))
            }

        }
        return gMailUserLiveData
    }

    fun sendResetPassword(email: String): LiveData<Resource<User>> {
        when {
            TextUtils.isEmpty(email) -> {
                sendResetPasswordLiveData.postValue(Resource.error(null, "Enter registered email"))
            }
            networkControl.isConnected() -> {
                repository.sendForgotPassword(email).addOnCompleteListener { task ->
                    sendResetPasswordLiveData.postValue(Resource.loading(null))
                    if (task.isSuccessful) {
                        sendResetPasswordLiveData.postValue(Resource.success(User()))
                    } else {
                        sendResetPasswordLiveData.postValue(
                            Resource.error(
                                null,
                                task.exception?.message.toString()
                            )
                        )
                    }
                }
            }
            else -> {
                sendResetPasswordLiveData.postValue(Resource.error(null, "No internet connection"))
            }
        }
        return sendResetPasswordLiveData
    }


    ///////////////////////////////USER INFORMATION/////////////////////////////////////////////
     fun insertIntoUserTable(categories: User) = viewModelScope.launch {
       userData.insertToRoom(categories)
    }

    fun updateUserTable(categories: User) = viewModelScope.launch {
        userData.updateList(categories)
    }

    fun deleteAllUsers() = viewModelScope.launch {
        userData.deleteAllFromUsers()
    }

    val getUserTable: LiveData<List<User>> = userData.getAllFromUsers().asLiveData()

     fun getUserEmailPassword(email: String, password: String): LiveData<List<User>> {
        return userData.userEmailPassword(email, password)
    }
}