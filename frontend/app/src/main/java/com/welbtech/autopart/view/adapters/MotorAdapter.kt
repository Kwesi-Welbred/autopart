package com.welbtech.autopart.view.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.android.automobile.model.MotorAccessories
import com.bumptech.glide.Glide
import com.welbtech.autopart.R
import com.welbtech.autopart.databinding.ProductListBinding

class MotorAdapter(private val impl: MotorImpl) :
    ListAdapter<MotorAccessories, MotorAdapter.RecyclerViewHolder>(ListComparator()) {

    // val context = AutoMobileApp.roomDatabaseInstance
    //  val repository = context.favDao()

    //bind the recycler list items
    inner class RecyclerViewHolder(val binding: ProductListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(list: MotorAccessories?) {
            binding.productBrandName.text = list?.brandName
            binding.productPrice.text = "Ghc " + list?.price
            binding.productImageSingleProduct.load(list?.imgUrl)
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
        Glide.with(holder.itemView.context).load(getItemPosition.imgUrl)
            .into(holder.binding.productImageSingleProduct)

        holder.binding.productAddToFavSingleProduct.apply {
            setOnClickListener {
                impl.onAddToFavoriteListener(getItemPosition)
                /*val list = mutableListOf(
              Favorites(
                  imgUrl = getItemPosition.imgUrl,
                  price = getItemPosition.price,
                  brandName = getItemPosition.brandName
              )
          )
          runBlocking {
              repository.insertToRoom(list)
          }*/

                Log.d("FAVORITES", "$getItemPosition")
                this.setImageResource(R.drawable.ic_favorite)
            }
        }
        holder.binding.productRating.setOnClickListener {
            Log.d("ITEM CLICKED", "::::::::::::::::::YOU CLICK")
        }

        holder.itemView.setOnClickListener {
            impl.onViewDetailsListener(getItemPosition)

        }
    }

    class ListComparator : DiffUtil.ItemCallback<MotorAccessories>() {
        override fun areItemsTheSame(
            oldItem: MotorAccessories,
            newItem: MotorAccessories
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: MotorAccessories,
            newItem: MotorAccessories
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    interface MotorImpl {
        fun onViewDetailsListener(cart: MotorAccessories)
        fun onAddToFavoriteListener(favorites: MotorAccessories)
        fun onAddRatingListener(rating: Float)
    }

}