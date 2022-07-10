package com.android.automobile.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.automobile.R
import com.android.automobile.databinding.FragmentOrderBinding
import com.android.automobile.view.activities.HomeActivity
import com.android.automobile.view.util.bottomOnNavOnBackPress
import com.android.automobile.view.util.navById

class OrderFragment : Fragment() {
   private lateinit var binding: FragmentOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentOrderBinding.inflate(layoutInflater)
        binding.contShopping.setOnClickListener {
            (activity as HomeActivity).binding.coordinator.visibility = View.VISIBLE
            navById(R.id.action_orderFragment_to_homeFragment)
        }

        binding.checkOutPage.setOnClickListener {
            navById(R.id.action_orderFragment_to_orderDetailsFragment)
        }
        return binding.root
    }


}