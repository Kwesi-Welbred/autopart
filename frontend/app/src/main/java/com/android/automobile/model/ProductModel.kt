package com.android.automobile.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


sealed class ProductModel {

    @Entity(tableName = "car_accessories")
    data class CarAccessories(
        @PrimaryKey(autoGenerate = true) var id: Int = 0,
        @ColumnInfo(name = "rating") val rating: String? = null,
        @ColumnInfo(name = "brand") val brandName: String? = null,
        @ColumnInfo(name = "price") val price: String? = null,
        @ColumnInfo(name = "img_url") val imgUrl: String? = null
    ) : ProductModel()


    @Entity(tableName = "motor_accessories")
    data class MotorAccessories(
        @PrimaryKey(autoGenerate = true) var id: Int = 0,
        @ColumnInfo(name = "rating") val rating: String? = null,
        @ColumnInfo(name = "brand") val brandName: String? = null,
        @ColumnInfo(name = "price") val price: String? = null,
        @ColumnInfo(name = "img_url") val imgUrl: String? = null
    ) : ProductModel()


    @Entity(tableName = "cover_page")
    data class CoverPage(
        @PrimaryKey(autoGenerate = true) var id: Int = 0,
        @ColumnInfo(name = "rating") val rating: String? = null,
        @ColumnInfo(name = "name") val brandName: String? = null,
        @ColumnInfo(name = "price") val price: String? = null,
        @ColumnInfo(name = "img_url") val imgUrl: String? = null
    ) : ProductModel()
}
