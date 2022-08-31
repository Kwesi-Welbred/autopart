package com.welbtech.autopart.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.welbtech.autopart.R
import com.welbtech.autopart.databinding.FragmentCategoryDetailsBinding
import com.welbtech.autopart.model.CatItems
import com.welbtech.autopart.view.adapters.CategoryViewAdapter
import com.welbtech.autopart.view.fragments.ProductCategoryFragment.Companion.BATTERIES
import com.welbtech.autopart.view.fragments.ProductCategoryFragment.Companion.BELTS
import com.welbtech.autopart.view.fragments.ProductCategoryFragment.Companion.BRAKE
import com.welbtech.autopart.view.fragments.ProductCategoryFragment.Companion.ENGINE
import com.welbtech.autopart.view.fragments.ProductCategoryFragment.Companion.KEY
import com.welbtech.autopart.view.fragments.ProductCategoryFragment.Companion.LIGHT
import com.welbtech.autopart.view.fragments.ProductCategoryFragment.Companion.TOOLS
import com.welbtech.autopart.view.fragments.ProductCategoryFragment.Companion.TYRES


class CategoryDetailsFragment : Fragment(), CategoryViewAdapter.CatImpl {
    private lateinit var binding: FragmentCategoryDetailsBinding

    private val categoryAdapter: CategoryViewAdapter by lazy {
        CategoryViewAdapter(this) {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentCategoryDetailsBinding.inflate(layoutInflater)

        val recycler = binding.categoriesListRecView
        recycler.adapter = categoryAdapter
        recycler.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false).apply {
                recycler.layoutManager = this
            }
        initRecView()

        return binding.root


    }


    private fun engine() = arguments?.let {
        val details = it.getParcelableArrayList<CatItems>(ENGINE)
        val list = mutableListOf<CatItems>()
        details?.forEach { catItems ->
            list.add(catItems)
        }
        categoryAdapter.submitList(list)
    }


    private fun light() = arguments?.let {
        val details = it.getParcelableArrayList<CatItems>(LIGHT)
        val list = mutableListOf<CatItems>()
        details?.forEach { catItems ->
            list.add(catItems)
        }
        categoryAdapter.submitList(list)
    }

    private fun brake() = arguments?.let {
        val details = it.getParcelableArrayList<CatItems>(BRAKE)
        val list = mutableListOf<CatItems>()
        details?.forEach { catItems ->
            list.add(catItems)
        }
        categoryAdapter.submitList(list)
    }

    private fun tools() = arguments?.let {
        val details = it.getParcelableArrayList<CatItems>(TOOLS)
        val list = mutableListOf<CatItems>()
        details?.forEach { catItems ->
            list.add(catItems)
        }
        categoryAdapter.submitList(list)
    }

    private fun batteries() = arguments?.let {
        val details = it.getParcelableArrayList<CatItems>(BATTERIES)
        val list = mutableListOf<CatItems>()
        details?.forEach { catItems ->
            list.add(catItems)
        }
        categoryAdapter.submitList(list)
    }


    private fun tyres() = arguments?.let {
        val details = it.getParcelableArrayList<CatItems>(TYRES)
        val list = mutableListOf<CatItems>()
        details?.forEach { catItems ->
            list.add(catItems)
        }
        categoryAdapter.submitList(list)
    }

    private fun belts() = arguments?.let {
        val details = it.getParcelableArrayList<CatItems>(BELTS)
        val list = mutableListOf<CatItems>()
        details?.forEach { catItems ->
            list.add(catItems)
        }
        categoryAdapter.submitList(list)
    }


    private fun initRecView() {

        when (arguments?.getString(KEY)) {
            getString(R.string.engine_block) -> {
                binding.catName.text = getString(R.string.engine_block)
                engine()
            }

            getString(R.string.light_systems) -> {
                binding.catName.text = getString(R.string.light_systems)
                light()
            }

            getString(R.string.brakes) -> {
                binding.catName.text =   getString(R.string.brakes)
                brake()
            }

            getString(R.string.tools) -> {
                binding.catName.text =  getString(R.string.tools)
                tools()
            }

            getString(R.string.batteries) -> {
                binding.catName.text =  getString(R.string.batteries)
                batteries()
            }

            getString(R.string.fan_belts) -> {
                binding.catName.text = getString(R.string.fan_belts)
                belts()
            }

            getString(R.string.wheels_tyres) -> {
                binding.catName.text = getString(R.string.wheels_tyres)
                tyres()
            }

        }

    }

    override fun onItemViewListener(cat: CatItems) {
        val bundle = Bundle().apply {
            putString(KEY, "catItems")
            putParcelable("cat", cat)
        }
        findNavController().navigate(R.id.action_categoryDetailsFragment_to_detailsFragment, bundle)
    }
}