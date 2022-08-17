@file:Suppress("DEPRECATION", "LocalVariableName")

package com.android.automobile.view.util

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.Uri
import android.provider.MediaStore
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.io.File
import java.net.URI
import java.net.URISyntaxException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * @author Created by Peacemaker Otoo on April 2022
 * revised from Roman
 */
object UtilityMethods {

    var pd: ProgressDialog? = null

    /**
     *
     * 1 byte = 0.0078125 kilobits
     * 1 kilobits = 0.0009765625 megabit
     *
     * @param downloadTime in milliseconds
     * @param bytesIn number of bytes downloaded
     * @return SpeedInfo containing current speed
     */

    var megabits = 0.0

    @SuppressLint("SimpleDateFormat")
    fun parseDateTimeTodMMMyyyy(time: String?): String? {
        val inputPattern = "yyyy-MM-dd hh:mm:ss"
        val outputPattern = "d MMM yyyy"
        val inputFormat = SimpleDateFormat(inputPattern)
        val outputFormat = SimpleDateFormat(outputPattern)
        var date: Date? = null
        var str: String? = null
        try {
            date = inputFormat.parse(time)
            str = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return str
    }

    @SuppressLint("SimpleDateFormat")
    fun parseDateTimeTodMMMyyyy2(time: String?): String? {
        val inputPattern = "yyyy-MM-dd"
        val outputPattern = "d MMM yy"
        val inputFormat = SimpleDateFormat(inputPattern)
        val outputFormat = SimpleDateFormat(outputPattern)
        val date: Date
        var str: String? = null
        try {
            date = time?.let { inputFormat.parse(it) } as Date
            str = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return str
    }

    fun emailValidator(email: String?): Boolean {
        val pattern: Pattern
        val EMAIL_PATTERN =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        pattern = Pattern.compile(EMAIL_PATTERN)
        val matcher: Matcher? = email?.let { pattern.matcher(it) }
        return matcher?.matches() ?: false
    }

     fun String.isEmailValid(): Boolean {
        return this.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }


    fun getFileNameByUri(context: Context, uri: Uri): String? {
        var filepath: String? = "" //default fileName
        //Uri filePathUri = uri;
        val file: File
        when {
            uri.scheme.toString().compareTo("content") == 0 -> {
                val cursor = context.contentResolver.query(
                    uri,
                    arrayOf(
                        MediaStore.Images.ImageColumns.DATA,
                        MediaStore.Images.Media.ORIENTATION
                    ),
                    null,
                    null,
                    null
                )
                val column_index = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor.moveToFirst()
                val mImagePath = cursor.getString(column_index)
                cursor.close()
                filepath = mImagePath
            }
            uri.scheme!!.compareTo("file") == 0 -> {
                try {
                    file = File(URI(uri.toString()))
                    if (file.exists()) filepath = file.absolutePath
                } catch (e: URISyntaxException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }
            }
            else -> {
                filepath = uri.path
            }
        }
        return filepath
    }

    fun hideKeyboard(context: Context, view: View) {
        // hide virtual keyboard
        val imm =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(
            view.windowToken,
            InputMethodManager.RESULT_UNCHANGED_SHOWN
        )
    }

    val timeStamp: String
        get() = Date().time.toString()



    fun getCurrentDate(): String {
        val c = Calendar.getInstance()
        val day: Int = c.get(Calendar.DAY_OF_MONTH)
        val month: Int = c.get(Calendar.MONTH) + 1
        val year: Int = c.get(Calendar.YEAR)
        return "$day-$month-$year"
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val conMan = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = conMan.activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) { // connected to the internet
            if (activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI) { // connected to wifi
                return true
            } else if (activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE) { // connected to the mobile provider's authData plan
                return true
            }
        }
        return (conMan.activeNetworkInfo != null && conMan.activeNetworkInfo!!.isConnected)
    }

    fun isValidPhoneNumber(target: CharSequence?): Boolean {
        return if (target == null) {
            false
        } else {
            if (target.length < 10 || target.length > 10) {
                false
            } else {
                Patterns.PHONE.matcher(target).matches()
            }
        }
    }





}