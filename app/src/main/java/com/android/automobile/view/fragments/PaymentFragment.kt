package com.android.automobile.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.automobile.R
import com.android.automobile.databinding.FragmentPaymentBinding
import com.android.automobile.di.AutoMobileApp
import com.android.automobile.model.Cart
import com.android.automobile.view.adapters.CartAdapter
import com.android.automobile.view.util.navById
import com.android.automobile.viewmodel.factories.ProductViewModelFactory
import com.android.automobile.viewmodel.products.ProductViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog


class PaymentFragment : Fragment(), CartAdapter.CartImpl {
    lateinit var binding: FragmentPaymentBinding

     private val recyclerAdapter: CartAdapter by lazy {
            CartAdapter(this)
        }

     private val productViewmodel: ProductViewModel by viewModels {
        ProductViewModelFactory((activity?.application as AutoMobileApp).productRepository)
    }

    private var cartItems = mutableListOf<Cart>()
    private val totalCartPrice= MutableLiveData<Int>()
    private var quantity: Int = 0
    private var amt: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPaymentBinding.inflate(layoutInflater)
        binding.checkOutPage.setOnClickListener {
            //  onRadioButtonClicked(it)
            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.card_details, null)

            val payBtn = view.findViewById<Button>(R.id.payment)
            payBtn.setOnClickListener {
                navById(R.id.action_paymentFragment_to_orderFragment)
                dialog.dismiss()
            }

            dialog.setCancelable(true)
            dialog.setContentView(view)
            dialog.show()
        }

        val cartView = binding.categoriesRecView
        cartView.adapter = recyclerAdapter
        cartView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        cartView.setHasFixedSize(true)

         productViewmodel.getAllFromCart.observe(viewLifecycleOwner, Observer {list->
            //recyclerAdapter.submitList(it)
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
             binding.itemCount.text = "Items $quantity"
        })

        totalCartPrice.observe(viewLifecycleOwner, Observer {expenses->
                Log.d("Total", "OBSERVER:::::::::::::::::::${expenses}")
                binding.order.text = "*Ghc $expenses"
            binding.totalPriceFrag.text = "*Ghc ${expenses+30}"
            })





        return binding.root
    }

    fun onRadioButtonClicked(view: View) {
        val dialog = BottomSheetDialog(requireContext())
        val viewLayoutInflater = layoutInflater.inflate(R.layout.card_details, null)
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.credit_card ->
                    if (checked) {
                        dialog.setCancelable(true)
                        dialog.setContentView(viewLayoutInflater)
                        dialog.show()
                    }
                R.id.paypal ->
                    if (checked) {
                        // Ninjas rule
                    }

                R.id.gpay ->
                    if (checked) {
                        // Ninjas rule
                    }
            }
        }
    }

    override fun onItemDeleteListener(car: Cart) {
        productViewmodel.deleteSingleCart(car)
    }

}