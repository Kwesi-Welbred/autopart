package com.android.automobile.view.adapters

import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.automobile.R
import com.android.automobile.model.CarAccessories
import com.android.automobile.model.CoverPage
import com.android.automobile.model.MotorAccessories
import com.android.automobile.view.util.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("coverProperties")
fun <T>bindCoverRecyclerView(recyclerView: RecyclerView, data: List<CoverPage>?){
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


/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
                .load(imgUri)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image))
                .into(imgView)
    }
}

/**
 * This binding adapter displays the [Status] of the network request in an image view.  When
 * the request is loading, it displays a loading_animation.  If the request has an error, it
 * displays a broken image to reflect the connection error.  When the request is finished, it
 * hides the image view.
 */
@BindingAdapter("productLoadingStatus")
fun bindStatus(statusImageView: ImageView, status: Status?) {
    when (status) {
        Status.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        Status.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        Status.SUCCESS -> {
            statusImageView.visibility = View.GONE
        }
        null -> TODO()
    }
}