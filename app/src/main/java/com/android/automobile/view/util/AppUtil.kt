package com.android.automobile.view.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import com.android.automobile.view.activities.AuthActivity
import com.android.automobile.view.activities.HomeActivity
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