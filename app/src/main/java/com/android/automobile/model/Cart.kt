package com.android.automobile.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import javax.inject.Inject


@Entity(tableName = "cart")
    data class Cart(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "brand") var brandName: String? = null,
    @ColumnInfo(name = "price") var price: String? = null,
    @ColumnInfo(name = "img_url") var imgUrl: String? = null,
    @ColumnInfo(name = "quantity") var quantity: Int
    )