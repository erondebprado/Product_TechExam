package com.davemac.product_techexam.Model.Products

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_products")
data class Product(

    @PrimaryKey(autoGenerate = false)
    val id: Int?=0,
    val description: String? = "",
    val price: Double?=0.0,
    val rating: Double?=0.0,
    val thumbnail: String?= null,
    val title: String?= null,
)