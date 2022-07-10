package com.android.automobile.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.automobile.R
import com.android.automobile.databinding.FragmentRecoveryBinding
import com.android.automobile.view.util.goToUrl


class RecoveryFragment : Fragment() {

    private lateinit var binding: FragmentRecoveryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecoveryBinding.inflate(layoutInflater)
        binding.openMail.setOnClickListener {
            val email = arguments?.getString("email")
            Log.d("EMAIL","::::::::::::::::::::::::::::::::${email}")
            requireContext().goToUrl("https://"+email!!)
        }
        return binding.root
    }

}