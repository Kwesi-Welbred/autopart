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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.automobile.R
import com.android.automobile.databinding.FragmentFavoriteBinding
import com.android.automobile.di.AutoMobileApp
import com.android.automobile.model.Favorites
import com.android.automobile.view.adapters.FavAdapter
import com.android.automobile.view.fragments.HomeFragment.Companion.imgUrl
import com.android.automobile.view.fragments.HomeFragment.Companion.price
import com.android.automobile.viewmodel.factories.ProductViewModelFactory
import com.android.automobile.viewmodel.products.ProductViewModel
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment(), FavAdapter.FavImpl {
    private lateinit var binding: FragmentFavoriteBinding
    private val productViewmodel: ProductViewModel by viewModels {
        ProductViewModelFactory((activity?.application as AutoMobileApp).productRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)

        val anyAdapter: FavAdapter by lazy {
            FavAdapter(this)
        }

        val recyclerView = binding.favRecView
        recyclerView.adapter = anyAdapter
        StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL).apply {
            recyclerView.layoutManager = this
        }
        //recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)

        productViewmodel.getAllFromFav.observe(viewLifecycleOwner, Observer {
            lifecycleScope.launch {
                anyAdapter.submitList(it)
            }
        })

        return binding.root
    }

    override fun onFavDeleteListener(favorites: Favorites) {
       productViewmodel.deleteSingleFav(favorites)
    }

    override fun onViewDetailsListener(favorites: Favorites) {
         val bundle = Bundle()
        bundle.putString(price, favorites.price)
        bundle.putString(imgUrl, favorites.imgUrl)
        bundle.putString("name", favorites.brandName)
        bundle.putString("rating", favorites.rating)
        findNavController().navigate(R.id.action_favoriteFragment_to_detailsFragment, bundle)
    }

    override fun onRatingListener(rating: Float) {
        TODO("Not yet implemented")
    }


}