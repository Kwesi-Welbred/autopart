package com.android.automobile.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "car_accessories")
    data class CarAccessories (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "rating") var rating: String? = null,
    @ColumnInfo(name = "brand") var brandName: String? = null,
    @ColumnInfo(name = "price") var price: String? = null,
    @ColumnInfo(name = "img_url") var imgSrcUrl: String? = null,
    @ColumnInfo(name = "product") var productName: String? = null
    )