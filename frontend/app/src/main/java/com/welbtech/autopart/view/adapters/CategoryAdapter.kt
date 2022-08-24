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
import com.welbtech.autopart.databinding.CategorySingleBinding
import com.welbtech.autopart.databinding.FavoritesListBinding
import com.welbtech.autopart.databinding.FragmentShopBinding
import com.welbtech.autopart.model.CatItems
import com.welbtech.autopart.model.Category

class CategoryAdapter(private var itemClickListener: ( title:String) -> Unit) :
    ListAdapter<CatItems, CategoryAdapter.RecyclerViewHolder>(ListComparator()) {
    //bind the recycler list items
    inner class RecyclerViewHolder(val binding: CategorySingleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(list: CatItems?) {
            binding.categoryNameCateSingle.text = list?.name
            binding.categoryImageCateSingle.load(list?.image)
        }
    }

    //inflate the List
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            CategorySingleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    //bind the model list the recycler list
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val getItemPosition = getItem(position)
        holder.bind(getItemPosition)
        Glide.with(holder.itemView.context).load(getItemPosition.image)
            .into(holder.binding.categoryImageCateSingle)

        holder.itemView.setOnClickListener {
            itemClickListener(getItemPosition.name)
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

}