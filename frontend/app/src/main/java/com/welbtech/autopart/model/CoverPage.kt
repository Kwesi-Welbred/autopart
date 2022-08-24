package com.welbtech.autopart.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cover_page")
    data class CoverPage(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "rating") var rating: String? = null,
    @ColumnInfo(name = "name") var brandName: String? = null,
    @ColumnInfo(name = "price") var price: String? = null,
    @ColumnInfo(name = "img_url") var imgUrl: String? = null
    )