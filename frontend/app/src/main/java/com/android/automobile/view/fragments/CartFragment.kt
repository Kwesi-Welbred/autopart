package com.android.automobile.view.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.automobile.R
import com.android.automobile.databinding.FragmentCartBinding
import com.android.automobile.di.AutoMobileApp
import com.android.automobile.model.Cart
import com.android.automobile.view.adapters.CartAdapter
import com.android.automobile.view.util.bottomOnNavOnBackPress
import com.android.automobile.view.util.hideBottomNavOnNav
import com.android.automobile.viewmodel.factories.ProductViewModelFactory
import com.android.automobile.viewmodel.products.ProductViewModel

class CartFragment : Fragment(), CartAdapter.CartImpl {
    lateinit var binding: FragmentCartBinding

    private var cartItems = mutableListOf<Cart>()
    private val totalCartPrice=MutableLiveData<Int>()
    private var quantity: Int = 0
    private var amt: Int = 0

    private val productViewmodel: ProductViewModel by viewModels {
        ProductViewModelFactory((activity?.application as AutoMobileApp).productRepository)
    }

    companion object {
        fun newInstance() = CartFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCartBinding.inflate(layoutInflater)

        val recyclerAdapter: CartAdapter by lazy {
            CartAdapter(this)
        }

        binding.checkOutPage.setOnClickListener {
            hideBottomNavOnNav(R.id.action_cartFragment_to_checkoutFragment)
        }

        val cartView = binding.cartRecView
        cartView.adapter = recyclerAdapter
        cartView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        cartView.setHasFixedSize(true)


        productViewmodel.getAllFromCart.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                recyclerAdapter.submitList(it)
                cartItems.clear()
                amt =0
                cartItems.addAll(it)
            }

            cartItems.forEach {
                  amt += it.price!!.toInt()
                 quantity += it.quantity
                 totalCartPrice.value = amt
                Log.d("AMOUNT", ":::::::::::::::::::${amt}")
            }

            Log.d("CART", "OBSERVER:::::::::::::::::::${totalCartPrice.value}")
        })
         totalCartPrice.observe(viewLifecycleOwner, Observer {expenses->
                Log.d("Total", "OBSERVER:::::::::::::::::::${expenses}")
                binding.totalPriceFrag.text = "*Ghc $expenses"
            })



        return binding.root
    }

    override fun onItemDeleteListener(car: Cart) {
        productViewmodel.deleteSingleCart(car)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        bottomOnNavOnBackPress(R.id.action_cartFragment_to_homeFragment)
    }

}