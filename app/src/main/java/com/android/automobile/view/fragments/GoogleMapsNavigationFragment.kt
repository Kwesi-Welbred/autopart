package com.android.automobile.view.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.android.automobile.databinding.FragmentGoogleMapsNavigationBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior


class GoogleMapsNavigationFragment : Fragment() {
    private lateinit var binding: FragmentGoogleMapsNavigationBinding

    private val getBottomSheet by lazy {
        binding.bottomSheet
    }

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentGoogleMapsNavigationBinding.inflate(layoutInflater)
        /*val webView = binding.webView
        webView.webViewClient = WebViewClient()
        webView.webChromeClient = WebChromeClient()
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("http://maps.google.com/maps?" + "saddr=4.908538200000001,-1.7563672" + "&daddr=4.989325299999999,-1.7562893")*/


        routUser("4.908538200000001,-1.7563672","4.989325299999999,-1.7562893")

        bottomSheetBehavior = BottomSheetBehavior.from(getBottomSheet.searchPlacesSheet)
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        Log.d("STATE", "STATE_COLLAPSED")
                    }

                    BottomSheetBehavior.STATE_DRAGGING -> {
                        Log.d("STATE", "STATE_COLLAPSED")
                    }

                    BottomSheetBehavior.STATE_EXPANDED -> {
                        Log.d("STATE", "STATE_EXPANDED")
                    }

                    BottomSheetBehavior.STATE_HIDDEN -> {
                        Log.d("STATE", "STATE_EXPANDED")
                    }

                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                slideOffset * 180
            }

        })

        return binding.root
    }

    fun routUser(source:String, destination:String){
        //check if map not installed
        try {
            //when map is installed, initialize uri
            val uri = Uri.parse("https://www.google.co.in/maps/dir/$source/$destination")

            val launchMap = Intent(Intent.ACTION_VIEW, uri)
            launchMap.setPackage("com.google.android.apps.maps")
            launchMap.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(launchMap)



        }catch (e:ActivityNotFoundException){
            val uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps")
            val goToPlayStoreIntent = Intent(Intent.ACTION_VIEW, uri)
            goToPlayStoreIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(goToPlayStoreIntent)
        }
    }

    private fun expandCollapseSheet() {
        if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            Log.d("CLOSE", "Close Bottom Sheet")
        } else {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            Log.d("CLOSE", "Show Bottom Sheet")
        }
    }
}