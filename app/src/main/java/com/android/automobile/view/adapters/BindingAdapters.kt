package com.android.automobile.view.adapters

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.automobile.model.CarAccessories
import com.android.automobile.model.CoverPage
import com.android.automobile.model.MotorAccessories

@BindingAdapter("coverProperties")
fun bindCoverRecyclerView(recyclerView: RecyclerView, data: List<CoverPage>?){
    val adapter = recyclerView.adapter as CoverAdapter
    adapter.submitList(data)
}

@BindingAdapter("carProperties")
fun bindCarRecyclerView(recyclerView: RecyclerView, data: List<CarAccessories>?){
    val adapter = recyclerView.adapter as CarAdapter
    adapter.submitList(data)
}


@BindingAdapter("motorProperties")
fun bindMotorRecyclerView(recyclerView: RecyclerView, data: List<MotorAccessories>?){
    val adapter = recyclerView.adapter as MotorAdapter
    adapter.submitList(data)
}