package com.android.automobile.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil.load
import com.android.automobile.databinding.FragmentProfileBinding
import com.android.automobile.view.fragments.LoginFragment.Companion.TAG
import com.android.automobile.viewmodel.products.HomeViewModel
import com.android.automobile.viewmodel.products.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    @Inject
    lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        binding.logOut.setOnClickListener {
            homeViewModel.logout(requireView())
        }

        lifecycleScope.launchWhenStarted {
             with(binding) {
                    profileEmail.text = homeViewModel.user?.email
                    profileName.text = homeViewModel.user?.displayName
                    profileImage.load(homeViewModel.user?.photoUrl)
                }
        }
        return binding.root
    }

    private fun updateUser() {
        homeViewModel.user!!.reload().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                //update


            } else {
                Log.e(TAG, "Failed to reload user", task.exception)
            }
        }
    }


}
