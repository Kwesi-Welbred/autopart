package com.android.automobile.view.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.welbtech.autopart.view.activities.AuthActivity
import com.welbtech.autopart.view.activities.HomeActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.snackbar.Snackbar

fun Context.startHomeActivity() =
    Intent(this, HomeActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

fun Context.startAuthActivity() =
    Intent(this, AuthActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }

fun View.showSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun Context.goToUrl(url: String) {
    if (url.startsWith("https://") || url.startsWith("http://")) {
        val uriUrl = Uri.parse(url)
        val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
        startActivity(launchBrowser)

    } else {
        Toast.makeText(this, "Invalid Url", Toast.LENGTH_SHORT).show()
    }


    //https://github.com/Drabu/PlaceAutocomplete/tree/master/app lib
    //https://github.com/Ikhiloya/GooglePlacesAutoCompleteTutorial
    // https://github.com/ShwetaChauhan18/GooglePlacesAutoComplete //work fine
    // https://stackoverflow.com/questions/24487889/how-to-show-google-map-navigation-url-in-webview-android
    // https://stackoverflow.com/questions/54668523/how-to-implement-google-places-autocomplete-programmatically

}


fun Context.glideLoader(
    imgView: ImageView,
    imgUri: String,
    fallback: Int? = null,
    placeholder: Int? = null,
    error: Int? = null
) {
    try {
        Glide.with(this)
            .asDrawable()
            .placeholder(placeholder ?: 0)
            .load(imgUri)
            .fallback(fallback ?: 0)
            .error(error).listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    TODO("Not yet implemented")
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    TODO("Not yet implemented")
                }

            }).into(imgView)
    } catch (ex: Exception) {
    }
}