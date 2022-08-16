package com.android.automobile.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.android.automobile.R
import com.android.automobile.databinding.FragmentDetailsBinding
import com.android.automobile.model.CarAccessories
import com.android.automobile.model.Cart
import com.android.automobile.model.Favorites
import com.android.automobile.model.MotorAccessories
import com.android.automobile.view.activities.HomeActivity
import com.android.automobile.view.adapters.CarAdapter
import com.android.automobile.view.adapters.MotorAdapter
import com.android.automobile.viewmodel.products.ProductViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class DetailsFragment : Fragment(), CarAdapter.CarImpl, MotorAdapter.MotorImpl {
    private val productViewmodel: ProductViewModel by viewModels()

    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        val coverAdapter: CarAdapter by lazy {
            CarAdapter(this)
        }

        val coverRecyclerView = binding.RecomRecViewProductDetailsPage
        coverRecyclerView.adapter = coverAdapter
        coverRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        coverRecyclerView.setHasFixedSize(true)

        productViewmodel.getAllFromCars.observe(viewLifecycleOwner, Observer {
            lifecycleScope.launch {
                coverAdapter.submitList(it)
            }
        })

        val motorAdapter: MotorAdapter by lazy {
            MotorAdapter(this)
        }

        val motorRecyclerView = binding.RecomRecViewProductDetailsPage
        motorRecyclerView.adapter = motorAdapter
        motorRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        motorRecyclerView.setHasFixedSize(true)

        productViewmodel.getAllFromMotors.observe(viewLifecycleOwner, Observer {
            lifecycleScope.launch {
                motorAdapter.submitList(it)
            }
        })

        with(binding) {
            // productRatingSingleProduct.numStars = arguments?.getString("rating")?.toIntOrNull()!!
            productNameProductDetailsPage.text = arguments?.getString("name")
            productPriceProductDetailsPage.text = arguments?.getString("price")
            productImageProductDetailsPage.load(arguments?.getString("imgUrl"))

            val imageUrl = arguments?.getString("imgUrl")
            val name = arguments?.getString("name")
            val price = arguments?.getString("price")
            addToCartProductDetailsPage.setOnClickListener {
                val dialog = BottomSheetDialog(requireContext())
                val view = layoutInflater.inflate(R.layout.add_to_cart_bottom_sheet, null)
                val addToCart = view.findViewById<Button>(R.id.addToCart_BottomSheet)
                val qty = view.findViewById<TextView>(R.id.quantityEtBottom)
                val amt = view.findViewById<TextView>(R.id.total_amount)
                amt.text = price
                val neg = view.findViewById<LinearLayout>(R.id.minusLayout)
                val pos = view.findViewById<LinearLayout>(R.id.plusLayout)


                var incrementAmt = 0
                val totalAmt = Integer.parseInt(amt.text.toString())
                var incrementNum = Integer.parseInt(qty.text.toString())

                pos.setOnClickListener {
                    incrementNum += 1
                    incrementAmt = totalAmt * incrementNum
                    qty.text = incrementNum.toString()
                    amt.text = incrementAmt.toString()
                }

                neg.setOnClickListener {
                    if (incrementAmt > 0) {
                        incrementNum -= 1
                        incrementAmt = totalAmt * incrementNum
                        qty.text = incrementNum.toString()
                        amt.text = incrementAmt.toString()
                    }
                }

                addToCart.setOnClickListener {
                    val list = mutableListOf(
                        Cart(
                            brandName = name,
                            imgUrl = imageUrl,
                            price = amt.text.toString(),
                            quantity = qty.text.toString().toInt()
                        )
                    )
                    productViewmodel.insertIntoCart(list)
                    dialog.dismiss()
                }


                dialog.setCancelable(false)
                dialog.setContentView(view)
                dialog.show()
            }
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.action_detailsFragment_to_homeFragment)
                (activity as HomeActivity).binding.coordinator.visibility = View.VISIBLE
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(
            this, callback
        )

    }


    override fun onViewDetailListener(car: CarAccessories) {
        with(binding) {
            productNameProductDetailsPage.text = car.brandName
            productPriceProductDetailsPage.text = car.price
            productImageProductDetailsPage.load(car.imgSrcUrl)
        }
    }

    override fun onAddToFavoriteListener(favorites: CarAccessories) {
        val list = mutableListOf(
            Favorites(
                imgUrl = favorites.imgSrcUrl,
                price = favorites.price,
                brandName = favorites.brandName
            )
        )
        Timber.d("INSERTING.......", "$list")
        productViewmodel.insertIntoDatabase(list)
    }


    override fun onViewDetailsListener(cart: MotorAccessories) {
        with(binding) {
            productNameProductDetailsPage.text = cart.brandName
            productPriceProductDetailsPage.text = cart.price
            productImageProductDetailsPage.load(cart.imgUrl)

        }
    }

    override fun onAddToFavoriteListener(favorites: MotorAccessories) {
        val list = mutableListOf(
            Favorites(
                imgUrl = favorites.imgUrl,
                price = favorites.price,
                brandName = favorites.brandName
            )
        )
        Timber.d("INSERTING.......", "$list")
        productViewmodel.insertIntoDatabase(list)
    }

    override fun onAddRatingListener(rating: Float) {
        TODO("Not yet implemented")
    }

    override fun onRatingListener(rating: Float) {
        TODO("Not yet implemented")
    }
}