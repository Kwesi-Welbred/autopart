package com.welbtech.autopart.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class Category(
    val Name: String,
    val Image: Int,
)

@Parcelize
data class CatItems(
    val name: String,
    val image: Int,
    val price: String?=null,
):Parcelable