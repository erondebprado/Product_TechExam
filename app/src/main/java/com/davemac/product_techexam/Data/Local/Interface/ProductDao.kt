package com.davemac.product_techexam.Data.Local.Interface

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.davemac.product_techexam.Model.Products.Product
import com.davemac.product_techexam.Model.Products.ProductModel
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(product: Product) : Long

    @Query("SELECT * FROM tbl_products")
    fun getAllProducts(): Flow<List<Product>>
}