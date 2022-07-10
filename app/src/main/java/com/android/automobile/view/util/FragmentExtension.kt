package com.android.automobile.view.util

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.automobile.R
import com.android.automobile.view.activities.HomeActivity

var dialog: AlertDialog? = null

fun Fragment.loadingDialog() {
    val builder = AlertDialog.Builder(requireActivity())
    val inflater = activity?.layoutInflater
    builder.setView(inflater?.inflate(R.layout.custom_dialog, null))
    builder.setCancelable(true)
    builder.setOnCancelListener {
        it.dismiss()
    }
    dialog = builder.create()
    dialog!!.show()
}

fun Fragment.dismissDialog() {
    dialog!!.dismiss()
}

fun Fragment.bottomOnNavOnBackPress(directionId:Int) {
    val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().navigate(directionId)
            (activity as HomeActivity).binding.coordinator.visibility = View.VISIBLE
        }
    }
    activity?.onBackPressedDispatcher?.addCallback(
        this, callback
    )
}


fun Fragment.hideBottomNavOnNav(directionId: Int) {
    findNavController().navigate(directionId)
    (activity as HomeActivity).binding.coordinator.visibility = View.GONE
}

fun Fragment.navById(directionId: Int){
     findNavController().navigate(directionId)
}