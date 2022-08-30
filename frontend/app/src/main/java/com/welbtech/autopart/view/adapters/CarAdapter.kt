package com.welbtech.autopart.view.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.welbtech.autopart.model.CarAccessories
import com.welbtech.autopart.R
import com.welbtech.autopart.databinding.ProductListBinding
import com.welbtech.autopart.model.Cart
import com.welbtech.autopart.view.util.showSnackBar

class CarAdapter(private val impl: CarImpl) :
    ListAdapter<CarAccessories, CarAdapter.RecyclerViewHolder>(ListComparator()) {
    //bind the recycler list items
    inner class RecyclerViewHolder(val binding: ProductListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(list: CarAccessories?) {
            binding.productBrandName.text = list?.brandName
//            binding.productRating.rating = list?.rating!!.toFloat()
            binding.productPrice.text = "Ghc " + list?.price
            binding.productName.text = "productName"
            //binding.carAccessories?.imgSrcUrl = list?.imgSrcUrl
            binding.productImageSingleProduct.load(list?.imgSrcUrl)
            binding.executePendingBindings()
        }
    }

    //inflate the List
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            ProductListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    //bind the model list the recycler list
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val getItemPosition = getItem(position)
        holder.bind(getItemPosition)
        holder.binding.productAddToFavSingleProduct.apply {
            setOnClickListener {
                impl.onAddToFavoriteListener(getItemPosition)
                this.setImageResource(R.drawable.ic_favorite)
                Log.d("FAVORITES", "$getItemPosition")
            }
        }



        holder.binding.productRating.setOnClickListener {
            Log.d("ITEM CLICKED", "::::::::::::::::::YOU CLICK")
            holder.binding.productRating.rating = getItemPosition.rating?.toFloat()!!
            getItemPosition.rating
            impl.onRatingListener(getItemPosition.rating!!.toFloat())
        }

        holder.itemView.setOnClickListener {
            impl.onViewDetailListener(getItemPosition)
        }

        holder.binding.addToCart.setOnClickListener {
            impl.onAddToCartListener(getItemPosition)
        }


    }

    class ListComparator : DiffUtil.ItemCallback<CarAccessories>() {
        override fun areItemsTheSame(oldItem: CarAccessories, newItem: CarAccessories): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CarAccessories, newItem: CarAccessories): Boolean {
            return oldItem.id == newItem.id
        }
    }

    interface CarImpl {
        fun onViewDetailListener(car: CarAccessories)
        fun onAddToFavoriteListener(favorites: CarAccessories)
        fun onAddToCartListener(cart: CarAccessories)
        fun onRatingListener(rating: Float)

    }

}