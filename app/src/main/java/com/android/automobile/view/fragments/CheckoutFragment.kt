package com.android.automobile.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.automobile.R
import com.android.automobile.databinding.FragmentCheckoutBinding
import com.android.automobile.di.AutoMobileApp
import com.android.automobile.model.Cart
import com.android.automobile.view.adapters.CartAdapter
import com.android.automobile.view.adapters.CheckoutAdapter
import com.android.automobile.view.util.bottomOnNavOnBackPress
import com.android.automobile.viewmodel.factories.ProductViewModelFactory
import com.android.automobile.viewmodel.products.ProductViewModel

class CheckoutFragment : Fragment(), CheckoutAdapter.CartImpl{

    lateinit var binding: FragmentCheckoutBinding

    private val productViewmodel: ProductViewModel by viewModels {
        ProductViewModelFactory((activity?.application as AutoMobileApp).productRepository)
    }

    private val recyclerAdapter: CheckoutAdapter by lazy {
        CheckoutAdapter(this)
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

        val cartView = binding.cartRecView
        cartView.adapter = recyclerAdapter
        cartView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        //cartView.setHasFixedSize(true)


        productViewmodel.getAllFromCart.observe(viewLifecycleOwner, Observer {
            recyclerAdapter.submitList(it)
        })
            return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bottomOnNavOnBackPress(R.id.action_checkoutFragment_to_cartFragment)
    }

    override fun onItemDeleteListener(car: Cart) {
        TODO("Not yet implemented")
    }

}