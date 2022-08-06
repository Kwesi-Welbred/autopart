package com.android.automobile.view.adapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.automobile.model.CarAccessories

@BindingAdapter("carProperties")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<CarAccessories>?){
    val adapter = recyclerView.adapter as CarAdapter
    adapter.submitList(data)
}