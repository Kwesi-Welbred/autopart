package com.welbtech.autopart.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.welbtech.autopart.view.activities.HomeActivity
import com.welbtech.autopart.view.util.navById
import com.welbtech.autopart.R
import com.welbtech.autopart.databinding.FragmentOrderBinding

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