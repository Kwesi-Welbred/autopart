package com.android.automobile.view.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.android.automobile.databinding.FavoritesListBinding
import com.android.automobile.databinding.ProductListBinding
import com.android.automobile.model.CarAccessories
import com.android.automobile.model.Favorites
import com.bumptech.glide.Glide

class FavAdapter(private val impl: FavImpl) :
    ListAdapter<Favorites, FavAdapter.RecyclerViewHolder>(ListComparator()) {
    //bind the recycler list items
    inner class RecyclerViewHolder( val binding: FavoritesListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(list: Favorites?) {
            binding.productBrandName.text = list?.brandName
//            binding.productRating.rating = list?.rating!!.toFloat()
            binding.productPrice.text = "Ghc " + list?.price
            binding.productName.text = list?.brandName
            binding.productImageSingleProduct.load(list?.imgUrl)
        }
    }

    //inflate the List
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            FavoritesListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    //bind the model list the recycler list
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val getItemPosition = getItem(position)
        holder.bind(getItemPosition)
          Glide.with(holder.itemView.context).load(getItemPosition.imgUrl)
            .into(holder.binding.productImageSingleProduct)

        holder.binding.productAddToFavSingleProduct.setOnClickListener {
            impl.onFavDeleteListener(getItemPosition)
        }
        holder.binding.productRating.setOnClickListener {
            Log.d("ITEM CLICKED", "::::::::::::::::::YOU CLICK")
            holder.binding.productRating.rating = getItemPosition.rating?.toFloat()!!
            getItemPosition.rating
            impl.onRatingListener(getItemPosition.rating!!.toFloat())
        }

        holder.itemView.setOnClickListener {
            impl.onViewDetailsListener(getItemPosition)
        }


    }

    class ListComparator : DiffUtil.ItemCallback<Favorites>() {
        override fun areItemsTheSame(oldItem: Favorites, newItem: Favorites): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Favorites, newItem: Favorites): Boolean {
            return oldItem.id == newItem.id
        }
    }

    interface FavImpl {
        fun onFavDeleteListener(favorites: Favorites)
        fun onViewDetailsListener(favorites: Favorites)
        fun onRatingListener(rating: Float)

    }

}