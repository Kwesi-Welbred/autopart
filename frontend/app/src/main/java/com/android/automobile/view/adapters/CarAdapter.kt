package com.android.automobile.view.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.android.automobile.R
import com.android.automobile.databinding.ProductListBinding
import com.android.automobile.di.AutoMobileApp
import com.android.automobile.model.CarAccessories

class CarAdapter(private val impl: CarImpl) :
    ListAdapter<CarAccessories, CarAdapter.RecyclerViewHolder>(ListComparator()) {
   // val context = AutoMobileApp.roomDatabaseInstance
   // val repository = context.favDao()

    //bind the recycler list items
    inner class RecyclerViewHolder(val binding: ProductListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(list: CarAccessories?) {
            binding.productBrandName.text = list?.brandName
//            binding.productRating.rating = list?.rating!!.toFloat()
            binding.productPrice.text = "Ghc " + list?.price
            binding.productName.text = list?.productName
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
        fun onRatingListener(rating: Float)

    }

}