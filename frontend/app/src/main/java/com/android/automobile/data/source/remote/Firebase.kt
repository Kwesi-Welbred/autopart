package com.android.automobile.data.source.remote

import androidx.lifecycle.MutableLiveData
import com.android.automobile.model.User
import com.android.automobile.view.util.Resource
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import timber.log.Timber
import javax.inject.Inject

class FireBaseSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val fireStore: FirebaseFirestore
) {

    private val sendResetPasswordLiveData = MutableLiveData<Resource<User>>()

    fun currentUser() = firebaseAuth.currentUser
    fun fetchUser() = fireStore.collection("users").get()

    fun signUpUser(fullName: String, email: String, password: String) =
        firebaseAuth.createUserWithEmailAndPassword(email, password)

    fun signInUser(email: String, password: String) =
        firebaseAuth.signInWithEmailAndPassword(email, password)

    fun signInWithGoogle(acct: GoogleSignInAccount) = firebaseAuth.signInWithCredential(
        GoogleAuthProvider.getCredential(acct.idToken, null)
    )

    fun sendForgotPassword(email: String) = firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                sendResetPasswordLiveData.postValue(Resource.success(User()))
            } else {
                sendResetPasswordLiveData.postValue(
                    Resource.error(
                        null,
                        message = task.exception?.message!!.toString()
                    )
                )
            }
        }

  //  fun verifyEmail(email: String) = firebaseAuth.currentUser?.sendEmailVerification(email)


    fun sendEmailVerification() {
        // Send verification email
       // val user = firebaseAuth.currentUser!!
        currentUser()?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.d("Verification email sent to ${currentUser()?.email} ")
                } else {
                    Timber.e(task.exception, "Failed to send verification email.")
                }
            }
    }

    fun saveUser(fullName:String, email: String, password: String) =
        fireStore.collection("users").document(email).set(
            User(
                uid = firebaseAuth.uid,
                fullName = fullName,
                email = email,
                password = password
            )
        )

    fun logout() = firebaseAuth.signOut()
    fun getUserCollection(users: String ) = fireStore.collection(users)

}
