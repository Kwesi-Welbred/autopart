package com.android.automobile.data.repository

import androidx.lifecycle.LiveData
import com.android.automobile.data.dao.UserDao
import com.welbtech.autopart.data.source.remote.FireBaseSource
import com.welbtech.autopart.model.User
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class UserAuthentication @Inject constructor(private val fireBaseSource: FireBaseSource) {

    fun signUpUser(fullName: String, email: String, password: String) =
        fireBaseSource.signUpUser(fullName,email, password)

    fun signInUser(email: String, password: String) = fireBaseSource.signInUser(email, password)

    fun saveUser(fullName: String, email: String, password: String) = fireBaseSource.saveUser(fullName, email, password)

    fun signInWithGoogle(acct: GoogleSignInAccount) = fireBaseSource.signInWithGoogle(acct)
    fun sendForgotPassword(email: String) = fireBaseSource.sendForgotPassword(email)

    val emailVerification = fireBaseSource.sendEmailVerification()
    fun userCollection(users:String) = fireBaseSource.getUserCollection(users)
    val getCurrentUser = fireBaseSource.currentUser()
    val searchUser = fireBaseSource.fetchUser()
    val singOutUser = fireBaseSource.logout()


}



//Getting user data from the data database
class GetUserData @Inject constructor(private val userDao: UserDao) : UserDao {
    override suspend fun insertToRoom(user: User) {
        return userDao.insertToRoom(user)
    }

    override suspend fun updateList(user: User) {
        return userDao.updateList(user)
    }

    override fun getAllFromUsers(): Flow<List<User>> {
        return userDao.getAllFromUsers()
    }

    override  fun userEmailPassword(
        email: String,
        password: String
    ): LiveData<List<User>> {
        return userDao.userEmailPassword(email, password)
    }

    override suspend fun deleteAllFromUsers() {
        return userDao.deleteAllFromUsers()
    }

}
