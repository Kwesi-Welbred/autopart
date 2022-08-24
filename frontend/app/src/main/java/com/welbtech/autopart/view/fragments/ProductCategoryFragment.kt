package com.welbtech.autopart.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.welbtech.autopart.R
import com.welbtech.autopart.databinding.FragmentShopBinding
import com.welbtech.autopart.model.CatItems
import com.welbtech.autopart.model.CoverPage
import com.welbtech.autopart.view.adapters.CategoryAdapter
import com.welbtech.autopart.view.adapters.CoverAdapter
import com.welbtech.autopart.viewmodel.products.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProductCategoryFragment : Fragment(), CoverAdapter.CoverImpl {
    private lateinit var binding: FragmentShopBinding
    private val productViewmodel: ProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentShopBinding.inflate(layoutInflater)

        initRecView()
        return binding.root
    }

    private fun initRecView() {

        val anyAdapter: CoverAdapter by lazy {
            CoverAdapter(this)
        }

        val categoryAdapter: CategoryAdapter by lazy {
            CategoryAdapter { title ->
                when (title) {
                    "Engine Block" -> {

                        val category = arrayListOf(
                            CatItems("Engine Block", R.drawable.engine_block, "Ghc 5000"),
                            CatItems("Engine Block", R.drawable.engine_1, "Ghc 5000"),
                            CatItems("Engine Block", R.drawable.engine_2, "Ghc 5000"),
                            CatItems("Engine Block", R.drawable.engine_3, "Ghc 5000"),
                            CatItems("Engine Block", R.drawable.engine_4, "Ghc 5000"),
                            CatItems("Engine Block", R.drawable.engine_5, "Ghc 5000"),
                            CatItems("Engine Block", R.drawable.engine_6, "Ghc 5000"),
                            CatItems("Engine Block", R.drawable.engine_7, "Ghc 5000"),
                        )

                        val bundle = Bundle().apply {
                            putString(KEY, title)
                            putParcelableArrayList(ENGINE, category)
                        }
                        navigateTo(bundle)
                    }
                    "Light Systems" -> {
                        val category = arrayListOf(
                            CatItems("Light Systems", R.drawable.car_light_2, "Ghc 1000"),
                            CatItems("Light Systems", R.drawable.car_light_3, "Ghc 1000"),
                            CatItems("Light Systems", R.drawable.car_light_4, "Ghc 1000"),
                            CatItems("Light Systems", R.drawable.car_light_5, "Ghc 1000"),
                            CatItems("Light Systems", R.drawable.car_light_2, "Ghc 1000"),
                            CatItems("Light Systems", R.drawable.spotlight, "Ghc 1000"),
                        )
                        val bundle = Bundle().apply {
                            putString(KEY, title)
                            putParcelableArrayList(LIGHT, category)
                        }
                        navigateTo(bundle)

                    }
                    "Brakes" -> {

                        val category = arrayListOf(
                            CatItems("Brakes", R.drawable.brakes, "Ghc 2000"),
                            CatItems("Brakes", R.drawable.brakes, "Ghc 2000"),
                            CatItems("Brakes", R.drawable.brakes, "Ghc 2000"),
                            CatItems("Brakes", R.drawable.brakes, "Ghc 2000"),
                            CatItems("Brakes", R.drawable.brakes, "Ghc 2000"),
                            CatItems("Brakes", R.drawable.brakes, "Ghc 2000"),
                        )

                        val bundle = Bundle().apply {
                            putString(KEY, title)
                            putParcelableArrayList(BRAKE, category)
                        }
                        navigateTo(bundle)

                    }
                    "Tools" -> {
                        val category = arrayListOf(
                            CatItems("Tools", R.drawable.tools, "Ghc 300"),
                            CatItems("Tools", R.drawable.tools_1, "Ghc 300"),
                            CatItems("Tools", R.drawable.tools_2, "Ghc 300"),
                            CatItems("Tools", R.drawable.tools_3, "Ghc 300"),
                            CatItems("Tools", R.drawable.tools_4, "Ghc 300"),
                            CatItems("Tools", R.drawable.tools_5, "Ghc 300"),
                            CatItems("Tools", R.drawable.tools_6, "Ghc 300"),
                        )

                        val bundle = Bundle().apply {
                            putString(KEY, title)
                            putParcelableArrayList(TOOLS, category)
                        }
                        navigateTo(bundle)
                    }
                    "Batteries" -> {
                        val category = arrayListOf(
                            CatItems("Batteries", R.drawable.battery_1, "Ghc 400"),
                            CatItems("Batteries", R.drawable.battery_2, "Ghc 400"),
                            CatItems("Batteries", R.drawable.battery_3, "Ghc 400"),
                            CatItems("Batteries", R.drawable.battery_4, "Ghc 400"),
                            CatItems("Batteries", R.drawable.battery_5, "Ghc 400"),
                            CatItems("Batteries", R.drawable.battery_6, "Ghc 400")
                        )
                        val bundle = Bundle().apply {
                            putString(KEY, title)
                            putParcelableArrayList(BATTERIES, category)
                        }
                        navigateTo(bundle)
                    }
                    "Fan Belts" -> {
                        val category = arrayListOf(
                            CatItems("Fan Belts", R.drawable.belt_1, "Ghc 200"),
                            CatItems("Fan Belts", R.drawable.belt_2, "Ghc 200"),
                            CatItems("Fan Belts", R.drawable.belt_3, "Ghc 200"),
                            CatItems("Fan Belts", R.drawable.belt_4, "Ghc 200"),
                            CatItems("Fan Belts", R.drawable.belt_5, "Ghc 200"),
                            CatItems("Fan Belts", R.drawable.belt_6, "Ghc 200"),
                            CatItems("Fan Belts", R.drawable.belt_6, "Ghc 200"),
                            CatItems("Fan Belts", R.drawable.fan_belt, "Ghc 200")

                        )

                        val bundle = Bundle().apply {
                            putString(KEY, title)
                            putParcelableArrayList(BELTS, category)
                        }
                        navigateTo(bundle)

                    }


                     "Wheels and Tyres" -> {
                        val category = arrayListOf(
                            CatItems("Fan Belts", R.drawable.tyres, "Ghc 200"),
                            CatItems("Fan Belts", R.drawable.tyres_1, "Ghc 200"),
                            CatItems("Fan Belts", R.drawable.tyres_3, "Ghc 200"),
                            CatItems("Fan Belts", R.drawable.tyres_2, "Ghc 200"),
                            CatItems("Fan Belts", R.drawable.tyres_4, "Ghc 200"),
                            CatItems("Fan Belts", R.drawable.tyres_6, "Ghc 200"),
                            CatItems("Fan Belts", R.drawable.tyre, "Ghc 200"),
                            CatItems("Fan Belts", R.drawable.tyres_3, "Ghc 200")

                        )

                        val bundle = Bundle().apply {
                            putString(KEY, title)
                            putParcelableArrayList(TYRES, category)
                        }
                        navigateTo(bundle)

                    }
                }
            }
        }


        val recyclerView = binding.coverRecViewShopFrag
        recyclerView.adapter = anyAdapter
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)


        val recycler = binding.categoriesRecView
        recycler.adapter = categoryAdapter
        StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL).apply {
            recycler.layoutManager = this
            categoryAdapter.submitList(category)
        }
    }

    private val category = mutableListOf(
        CatItems("Engine Block", R.drawable.engine_block),
        CatItems("Light Systems", R.drawable.spotlight),
        CatItems("Brakes", R.drawable.brakes),
        CatItems("Wheels and Tyres", R.drawable.tyres),
        CatItems("Tools", R.drawable.tools),
        CatItems("Batteries", R.drawable.batteries),
        CatItems("Fan Belts", R.drawable.fan_belt)
    )


    override fun onViewDetails(cover: CoverPage) {
        TODO("Not yet implemented")
    }

    private fun navigateTo(bundle: Bundle) {
        if (findNavController().currentDestination != null)
            findNavController().navigate(R.id.action_profileFragment_to_categoryDetailsFragment,
                bundle)
    }


    companion object {
        const val ENGINE = "engine"
        const val BRAKE = "brake"
        const val BELTS = "belts"
        const val TOOLS = "tools"
        const val BATTERIES = "batteries"
        const val TYRES = "tyres"
        const val LIGHT = "light"
        const val KEY = "key"
    }


}