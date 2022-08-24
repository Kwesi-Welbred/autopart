package com.welbtech.autopart.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.welbtech.autopart.model.Cart
import com.bumptech.glide.Glide
import com.welbtech.autopart.databinding.CartListBinding

class CartAdapter(private val impl: CartImpl) :
    ListAdapter<Cart, CartAdapter.RecyclerViewHolder>(ListComparator()) {
    //bind the recycler list items
    inner class RecyclerViewHolder(val binding: CartListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(list: Cart?) {
            binding.cartImage.load(list?.imgUrl)
            binding.cartName.text = list?.brandName
            binding.cartPrice.text = list?.price
            binding.quantityTvCart.text = list?.quantity.toString()
        }
    }

    //inflate the List
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            CartListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    //bind the model list the recycler list
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val getItemPosition = getItem(position)
        holder.bind(getItemPosition)
        Glide.with(holder.itemView.context).load(getItemPosition.imgUrl)
            .into(holder.binding.cartImage)

        with(holder.binding) {
            var incrementAmt = 0
            val amt = Integer.parseInt(cartPrice.text.toString())
            var incrementNum = Integer.parseInt(quantityTvCart.text.toString())

            plusLayout.setOnClickListener {
                incrementNum += 1
                incrementAmt = amt * incrementNum
                quantityTvCart.text = incrementNum.toString()
                cartPrice.text = incrementAmt.toString()
                getItemPosition.price = incrementAmt.toString()
                getItemPosition.quantity = incrementNum

            }

            minusLayout.setOnClickListener {
                if (incrementAmt > 0) {
                    incrementNum -= 1
                    incrementAmt = amt * incrementNum
                    quantityTvCart.text = incrementNum.toString()
                    cartPrice.text = incrementAmt.toString()
                }
            }
        }

        holder.binding.cartMore.setOnClickListener {
            impl.onItemDeleteListener(getItemPosition)
        }
    }

    class ListComparator : DiffUtil.ItemCallback<Cart>() {
        override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean {
            return oldItem.id == newItem.id
        }
    }

    interface CartImpl {
        fun onItemDeleteListener(car: Cart)
    }

}