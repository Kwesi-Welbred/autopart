package com.android.automobile.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.android.automobile.databinding.CoverPageListBinding
import com.android.automobile.model.CoverPage
import com.bumptech.glide.Glide

class CoverAdapter(private val impl: CoverImpl) :
    ListAdapter<CoverPage, CoverAdapter.RecyclerViewHolder>(ListComparator()) {
    //bind the recycler list items
    inner class RecyclerViewHolder( val binding: CoverPageListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(list: CoverPage?) {
            binding.productNoteCover.text = list?.brandName
            binding.coverPage.load(list?.imgUrl)
        }
    }

    //inflate the List
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            CoverPageListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    //bind the model list the recycler list
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val getItemPosition = getItem(position)
        holder.bind(getItemPosition)
          Glide.with(holder.itemView.context).load(getItemPosition.imgUrl)
            .into(holder.binding.coverPage)
        holder.binding.productCheckCoverPage.setOnClickListener {
            impl.onViewDetails(getItemPosition)
        }

    }

    class ListComparator : DiffUtil.ItemCallback<CoverPage>() {
        override fun areItemsTheSame(oldItem: CoverPage, newItem: CoverPage): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CoverPage, newItem: CoverPage): Boolean {
            return oldItem.id == newItem.id
        }
    }

    interface CoverImpl {
        fun onViewDetails(cover: CoverPage)
    }

}