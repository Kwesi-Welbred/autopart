package com.android.automobile.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.automobile.R
import com.android.automobile.databinding.FragmentHomeBinding
import com.android.automobile.di.AutoMobileApp
import com.android.automobile.model.CarAccessories
import com.android.automobile.model.CoverPage
import com.android.automobile.model.Favorites
import com.android.automobile.model.MotorAccessories
import com.android.automobile.view.activities.HomeActivity
import com.android.automobile.view.adapters.CarAdapter
import com.android.automobile.view.adapters.CoverAdapter
import com.android.automobile.view.adapters.MotorAdapter
import com.android.automobile.viewmodel.factories.ProductViewModelFactory
import com.android.automobile.viewmodel.products.ProductViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeFragment : Fragment(), CarAdapter.CarImpl, MotorAdapter.MotorImpl,
    CoverAdapter.CoverImpl {
    private lateinit var binding: FragmentHomeBinding

    private val productViewmodel: ProductViewModel by viewModels {
        ProductViewModelFactory((activity?.application as AutoMobileApp).productRepository)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.lifecycleOwner = this
        binding.productVm = productViewmodel
        initRecViews()


        binding.carViewAll.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_detailsFragment)
            bottomNavVisibilityGone()
        }

        return binding.root
    }



    private fun initRecViews(){
        val coverRecyclerView = binding.coverRecView
        coverRecyclerView.adapter = CoverAdapter(this)
        coverRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        coverRecyclerView.setHasFixedSize(true)

        val motorRecyclerView = binding.motorRecycler
        motorRecyclerView.adapter = MotorAdapter(this)
        motorRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        motorRecyclerView.setHasFixedSize(true)


        val recyclerView = binding.carProductRecycler
        recyclerView.adapter = CarAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)

    }

    override fun onViewDetailListener(car: CarAccessories) {
        val bundle = Bundle()
        bundle.putString(price, car.price)
        bundle.putString(imgUrl, car.imgSrcUrl)
        bundle.putString("name", car.brandName)
        bundle.putString("rating", car.rating)
        findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
        bottomNavVisibilityGone()
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

    override fun onRatingListener(rating: Float) {
        TODO("Not yet implemented")
    }


    override fun onViewDetailsListener(cart: MotorAccessories) {
        val bundle = Bundle()
        bundle.putString(price, cart.price)
        bundle.putString(imgUrl, cart.imgUrl)
        bundle.putString("name", cart.brandName)
        bundle.putString("rating", cart.rating)
        findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
        bottomNavVisibilityGone()
    }

    override fun onAddToFavoriteListener(favorites: MotorAccessories) {
        val list = mutableListOf(
            Favorites(
                brandName = favorites.brandName,
                price = favorites.price,
                imgUrl = favorites.imgUrl,
                rating = favorites.rating
            )
        )
        productViewmodel.insertIntoDatabase(list)
    }

    override fun onAddRatingListener(rating: Float) {
        TODO("Not yet implemented")
    }

    override fun onViewDetails(cover: CoverPage) {
        val bundle = Bundle()
        bundle.putString(price, cover.price)
        bundle.putString(imgUrl, cover.imgUrl)
        bundle.putString("name", cover.brandName)
        bundle.putString("rating", cover.rating)
        bottomNavVisibilityGone()
        findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, bundle)

    }

    private fun bottomNavVisibilityGone() {
        val newBinding = (activity as HomeActivity).binding
        newBinding.coordinator.visibility = View.GONE
    }

    companion object {
        const val price = "price"
        const val size = "size"
        const val imgUrl = "imgUrl"
        const val type = "type"
    }


}