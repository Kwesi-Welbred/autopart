package com.welbtech.autopart.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.welbtech.autopart.model.User
import com.android.automobile.view.util.*
import com.android.automobile.view.util.UtilityMethods.isNetworkAvailable
import com.android.automobile.viewmodel.auths.LoginViewModel
import com.android.automobile.viewmodel.auths.RegisterViewModel
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.welbtech.autopart.R
import com.welbtech.autopart.databinding.RegisterFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@Suppress("DEPRECATION")
@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.register_fragment) {
    private var _binding: RegisterFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RegisterViewModel by viewModels()
    private val lViewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var auth: FirebaseAuth

    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = RegisterFragmentBinding.bind(view)
        // binding.signUpBtn.isEnabled = false
        // binding.emailEt.setOnClickListener { binding.signUpBtn.isEnabled = true }

        binding.signUpBtn.setOnClickListener {
            registerFun()
        }
        binding.loginTv.setOnClickListener {
            if (findNavController().currentDestination?.id == R.id.registerFragment) {
                NavHostFragment.findNavController(this)
                    .navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
        binding.googleSignIn.setOnClickListener {
            signInWithGoogle()
        }
    }

    @Deprecated("Deprecated in Java")//GOOGLE SIGN IN
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                lViewModel.signInWithGoogle(account!!).observe(viewLifecycleOwner, Observer {
                    when (it.status) {
                        Status.SUCCESS -> {
                            binding.signUpBtn.isEnabled = true
                            binding.normalLoader.visibility = View.INVISIBLE
                            if (findNavController().currentDestination?.id == R.id.registerFragment) {
                                context?.startHomeActivity()
                                Timber.d("display ${auth.currentUser?.displayName} ")
                            }
                            //save information to cloud
                            viewModel.saveUser(
                                auth.currentUser?.displayName!!,
                                auth.currentUser?.email!!, ""
                            )

                            //save information into local machine
                            viewModel.insertIntoUserTable(
                                        User(
                                            uid = auth.currentUser?.uid,
                                            fullName = auth.currentUser?.displayName,
                                            email = auth.currentUser?.email,
                                            password = "",
                                            imgUrl = auth.currentUser?.photoUrl.toString()//remember to use Uri.parse(this str)
                                        )
                                    )
                        }
                        Status.ERROR -> {
                            requireView().showSnackBar(it.message!!)
                        }
                        Status.LOADING -> {
                            binding.signUpBtn.isEnabled = false
                            binding.normalLoader.visibility = View.VISIBLE
                        }
                    }
                })
            } catch (e: ApiException) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signInWithGoogle() {
        if (isNetworkAvailable(requireContext())) {
            googleSignInClient.signOut()
            val signInIntent: Intent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, Constants.RC_SIGN_IN)
        } else {
            view?.showSnackBar("Please, check your internet connection")
        }

    }

    private fun registerFun() {
        if (isNetworkAvailable(requireContext())) {
            val firstName = binding.firstName.text.toString()
            val lastName = binding.lastNameEt.text.toString()
            val emailText = binding.emailEt.text?.trim().toString()
            val passwordText = binding.passwordEt.text.toString()
            val confirmPassword = binding.confirmPasswordEt.text.toString()
            val fullNameText = "$firstName $lastName}"
            when {
                !UtilityMethods.emailValidator(emailText) -> {
                    binding.emailIl.apply {
                        helperText = "Invalid email"
                        setBoxStrokeColorStateList(
                            AppCompatResources.getColorStateList(
                                requireContext(), R.color.primary
                            )
                        )
                    }
                    view?.showSnackBar("You entered incorrect email")
                }
                passwordText == confirmPassword -> {
                    viewModel.signUpUser(
                        email = emailText,
                        password = passwordText,
                        fullName = fullNameText
                    )
                        .observe(viewLifecycleOwner) {
                            when (it.status) {
                                Status.SUCCESS -> {
                                    binding.signUpBtn.isEnabled = true
                                    binding.normalLoader.visibility = View.VISIBLE
                                    viewModel.saveUser(
                                        it.data?.fullName.toString(),
                                        it.data?.email.toString().trim(),
                                        it.data?.password.toString()
                                    )
                                    viewModel.insertIntoUserTable(
                                        User(
                                            uid = it.data?.uid,
                                            fullName = it.data?.fullName,
                                            email = it.data?.email,
                                            password = it.data?.password
                                        )
                                    )
                                    binding.normalLoader.visibility = View.INVISIBLE
                                    auth.currentUser?.sendEmailVerification()
                                    view?.showSnackBar("User account registered")

                                    // navigateToLogin


                                    if (findNavController().currentDestination?.id == R.id.registerFragment) {
                                        NavHostFragment.findNavController(this)
                                            .navigate(R.id.action_registerFragment_to_loginFragment)
                                    }
                                }
                                Status.ERROR -> {
                                    binding.signUpBtn.isEnabled = true
                                    binding.normalLoader.visibility = View.INVISIBLE
                                    view?.showSnackBar(it.message!!)
                                }
                                Status.LOADING -> {
                                    binding.normalLoader.visibility = View.VISIBLE
                                    binding.signUpBtn.isEnabled = false
                                }
                            }
                        }
                }
                else -> {
                    binding.passwordIl.apply {
                        helperText = "Password mismatch"
                        setBoxStrokeColorStateList(
                            AppCompatResources.getColorStateList(
                                requireContext(), R.color.primary
                            )
                        )
                    }
                    binding.confirmPasswordLay.apply {
                        setBoxStrokeColorStateList(
                            AppCompatResources.getColorStateList(
                                requireContext(), R.color.primary
                            )
                        )
                        helperText = "Password mismatch"
                    }
                    view?.showSnackBar("Password mismatch")
                }
            }

        } else {
            view?.showSnackBar("Please, check your internet connection")
        }
    }


    /* val navigateToLogin = if (findNavController().currentDestination?.id == R.id.registerFragment) {
         NavHostFragment.findNavController(this)
             .navigate(R.id.action_registerFragment_to_loginFragment)
     } else {
              view?.showSnackBar("Can't navigate")
     }*/

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}