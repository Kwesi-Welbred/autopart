package com.welbtech.autopart.view.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.welbtech.autopart.model.Favorites
import com.bumptech.glide.Glide
import com.welbtech.autopart.databinding.*
import com.welbtech.autopart.model.CatItems
import com.welbtech.autopart.model.Category
import com.welbtech.autopart.view.fragments.CategoryDetailsFragment

class CategoryViewAdapter(private val impl: CatImpl,private var itemClickListener: (title:String) -> Unit ) :
    ListAdapter<CatItems, CategoryViewAdapter.RecyclerViewHolder>(ListComparator()) {
    //bind the recycler list items
    inner class RecyclerViewHolder(val binding: CategoryListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(list: CatItems?) {
            binding.productName.text = list?.name
            binding.productImageSingleProduct.load(list?.image)
            binding.productPrice.text = list?.price
        }
    }

    //inflate the List
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            CategoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    //bind the model list the recycler list
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val getItemPosition = getItem(position)
        holder.bind(getItemPosition)
        Glide.with(holder.itemView.context).load(getItemPosition.image)
            .into(holder.binding.productImageSingleProduct)

        holder.itemView.setOnClickListener {
            itemClickListener(getItemPosition.name)
            impl.onItemViewListener(getItemPosition)
        }

    }

    class ListComparator : DiffUtil.ItemCallback<CatItems>() {
        override fun areItemsTheSame(oldItem: CatItems, newItem: CatItems): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CatItems, newItem: CatItems): Boolean {
            return oldItem.name == newItem.name
        }
    }

    interface CatImpl {
        fun onItemViewListener(cat: CatItems)
    }

}