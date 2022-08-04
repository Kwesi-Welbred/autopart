package com.android.automobile.view.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.android.automobile.R
import com.android.automobile.databinding.FragmentCartBinding
import com.android.automobile.databinding.FragmentCheckoutBinding
import com.android.automobile.view.util.bottomOnNavOnBackPress

class CheckoutFragment : Fragment() {

lateinit var binding: FragmentCheckoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCheckoutBinding.inflate(layoutInflater)

        binding.proceed.setOnClickListener {
            findNavController().navigate(R.id.action_checkoutFragment_to_paymentFragment)
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bottomOnNavOnBackPress(R.id.action_checkoutFragment_to_cartFragment)
    }

}