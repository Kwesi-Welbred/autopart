package com.android.automobile.view.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.android.automobile.R
import com.android.automobile.databinding.LoginFragmentBinding
import com.android.automobile.view.activities.HomeActivity
import com.android.automobile.view.util.Constants.RC_SIGN_IN
import com.android.automobile.view.util.Status
import com.android.automobile.view.util.UtilityMethods
import com.android.automobile.view.util.showSnackBar
import com.android.automobile.viewmodel.auths.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@Suppress("DEPRECATION")
@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.login_fragment) {

    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!


    @Inject
    lateinit var googleSignInClient: GoogleSignInClient

    @Inject
    lateinit var auth: FirebaseAuth
    private val viewModel: LoginViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = LoginFragmentBinding.bind(view)

        binding.signUpTv.setOnClickListener {
            if (findNavController().currentDestination?.id == R.id.loginFragment) {
                findNavController()
                    .navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
        binding.loginBtn.setOnClickListener {
            val emailText = binding.emailEt.text?.toString()
            val passwordText = binding.passwordEt.text.toString()

            loginWithDB(emailText!!,passwordText)
        }

        binding.googleSignIn.setOnClickListener {
            signIn()
        }

        //forgot password
        val dialog = AlertDialog.Builder(requireContext())
        val inflater = (requireActivity()).layoutInflater
        val v = inflater.inflate(R.layout.fragment_forgot_password, null)
        dialog.setView(v).setCancelable(false)
        val d = dialog.create()
        val emailEt = v.findViewById<TextInputEditText>(R.id.emailEt)
        val sendBtn = v.findViewById<MaterialButton>(R.id.sendEmailBtn)
        val dismissBtn = v.findViewById<MaterialButton>(R.id.dismissBtn)


        sendBtn.setOnClickListener {
            viewModel.sendResetPassword(emailEt.text.toString()).observeForever {
                when (it.status) {
                    Status.LOADING -> {
                        binding.normalLoader.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        d.dismiss()
                        val bundle = Bundle()
                        bundle.putString("email", emailEt.text.toString())
                        Log.d("EMAIL", "${bundle.getString(" email ")}")
                        findNavController().navigate(
                            R.id.action_loginFragment_to_recoveryFragment,
                            bundle
                        )
                    }
                    Status.ERROR -> {
                        d.dismiss()
                        view.showSnackBar(it.message.toString())
                    }

                }
            }
        }
        dismissBtn.setOnClickListener {
            d.dismiss()
        }
        binding.forgottenPassTv.setOnClickListener {
            d.show()
        }
    }


    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {

            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)

                viewModel.signInWithGoogle(account!!).observe(viewLifecycleOwner) {
                    when (it.status) {
                        Status.SUCCESS -> {
                            if (findNavController().currentDestination?.id == R.id.loginFragment) {
                                startActivity(Intent(activity, HomeActivity::class.java))
                                Timber.d("$TAG ::::: ${auth.currentUser?.displayName} ")
                            }
                        }
                        Status.ERROR -> {
                            binding.normalLoader.visibility = View.INVISIBLE
                            requireView().showSnackBar(it.message!!)
                        }

                        Status.LOADING -> binding.normalLoader.visibility = View.VISIBLE
                    }
                }
            } catch (e: ApiException) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signIn() {
        val signInIntent: Intent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun loginWithDB(emailText: String, passwordText:String) {
        when {
            !UtilityMethods.emailValidator(emailText) -> {
                binding.emailError.setBoxStrokeColorStateList(
                    AppCompatResources.getColorStateList(
                        requireContext(), R.color.primary
                    )
                )
                view?.showSnackBar("Invalid email")
            }
            passwordText.isEmpty() -> {
                binding.passwordError.setBoxStrokeColorStateList(
                    AppCompatResources.getColorStateList(
                        requireContext(), R.color.primary
                    )
                )
                view?.showSnackBar("Password field cannot be empty")
            }

            else -> {
                try {
                     binding.normalLoader.visibility = View.VISIBLE
                    binding.loginBtn.isEnabled = false
                    viewModel.getUserTable
                        .observe(viewLifecycleOwner) {
                            it.forEach { detail ->
                                Log.d(TAG,"::::::::::::::::::::::$detail")
                                val email = detail.email
                                val password = detail.password

                                Log.d("DETAILS",":::::::::::::::::::::::::::::::$email, $password")
                                if (emailText == email && passwordText == password) {
                                    binding.normalLoader.visibility = View.INVISIBLE
                                    binding.loginBtn.isEnabled = true
                                     view?.showSnackBar("Login successful")
                                     if (findNavController().currentDestination?.id == R.id.loginFragment) {
                                         startActivity(
                                             Intent(
                                                 activity,
                                                 HomeActivity::class.java
                                             )
                                         )
                                         Timber.d("$TAG :::::::: ${auth.currentUser?.displayName} ")
                                     }

                                }else{
                                     binding.loginBtn.isEnabled = true
                                     binding.normalLoader.visibility = View.INVISIBLE
                                     view?.showSnackBar("Invalid email or password")
                                }

                            }
                        }
                } catch (e: Exception) {
                    binding.normalLoader.visibility = View.INVISIBLE
                    view?.showSnackBar(e.message!!)
                }
            }
        }
    }



    fun signInWithFirebase(emailText: String, passwordText:String){
         when {
                !UtilityMethods.emailValidator(emailText) -> {
                    binding.emailError.setBoxStrokeColorStateList(
                        AppCompatResources.getColorStateList(
                            requireContext(), R.color.primary
                        )
                    )
                    view?.showSnackBar("Invalid email")
                }
                passwordText.isEmpty() -> {
                    binding.passwordError.setBoxStrokeColorStateList(
                        AppCompatResources.getColorStateList(
                            requireContext(), R.color.primary
                        )
                    )
                    view?.showSnackBar("Password field cannot be empty")
                }

                else -> {

                    try {
                        viewModel.signInUser(emailText, passwordText)
                            .observe(viewLifecycleOwner) {
                                when (it.status) {
                                    Status.LOADING -> {
                                        binding.normalLoader.visibility = View.VISIBLE
                                        binding.loginBtn.isEnabled = false
                                    }

                                    Status.SUCCESS -> {
                                        binding.normalLoader.visibility = View.INVISIBLE
                                        view?.showSnackBar("Login successful")
                                        if (findNavController().currentDestination?.id == R.id.loginFragment) {
                                            startActivity(
                                                Intent(
                                                    activity,
                                                    HomeActivity::class.java
                                                )
                                            )
                                            Timber.d("$TAG :::::::: ${auth.currentUser?.displayName} ")
                                        }
                                    }
                                    Status.ERROR -> {
                                        binding.loginBtn.isEnabled = true
                                        binding.normalLoader.visibility = View.INVISIBLE
                                        view?.showSnackBar(it.message!!)
                                    }
                                }
                            }
                    } catch (e: Exception) {
                        binding.normalLoader.visibility = View.INVISIBLE
                        view?.showSnackBar(e.message!!)
                    }
                }
            }
    }


    companion object {
        val TAG: String = LoginFragment::class.java.simpleName
        fun newInstance() = LoginFragment()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
