package com.welbtech.autopart.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.automobile.view.util.goToUrl
import com.welbtech.autopart.databinding.FragmentRecoveryBinding


class RecoveryFragment : Fragment() {

    private lateinit var binding: FragmentRecoveryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecoveryBinding.inflate(layoutInflater)
        binding.openMail.setOnClickListener {
            val email = arguments?.getString("email")
            Log.d("EMAIL", "::::::::::::::::::::::::::::::::${email}")
            requireContext().goToUrl("https://" + email!!)
        }
        return binding.root
    }

}