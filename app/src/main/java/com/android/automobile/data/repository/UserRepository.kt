package com.android.automobile.data.repository

import com.android.automobile.data.dao.UserDao
import com.android.automobile.data.source.remote.FireBaseSource
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import javax.inject.Inject


class UserRepository @Inject constructor(private val fireBaseSource: FireBaseSource?=null,
                                         private val userDao: UserDao?=null) {

    fun signUpUser(fullName: String, email: String, password: String) =
        fireBaseSource?.signUpUser(fullName,email, password)

    fun signInUser(email: String, password: String) = fireBaseSource?.signInUser(email, password)

    fun saveUser(fullName: String, email: String, password: String) = fireBaseSource?.saveUser(fullName, email, password)

    fun signInWithGoogle(acct: GoogleSignInAccount) = fireBaseSource?.signInWithGoogle(acct)
    fun sendForgotPassword(email: String) = fireBaseSource?.sendForgotPassword(email)

    val emailVerification = fireBaseSource?.sendEmailVerification()
    fun userCollection(users:String) = fireBaseSource?.getUserCollection(users)
    val getCurrentUser = fireBaseSource?.currentUser()
    val searchUser = fireBaseSource?.fetchUser()
    val singOutUser = fireBaseSource?.logout()

    val signInWithRoom = userDao?.getAllFromUsers()


}

