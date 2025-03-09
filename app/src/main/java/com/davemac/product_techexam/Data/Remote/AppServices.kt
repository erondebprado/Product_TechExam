package com.davemac.product_techexam.Data.Remote

import com.davemac.product_techexam.Model.Categories.CategoriesModel
import com.davemac.product_techexam.Model.Categories.CategoriesModelItem
import com.davemac.product_techexam.Model.Products.Product
import com.davemac.product_techexam.Model.Products.ProductModel
import com.davemac.product_techexam.Utils.Constant.LIMIT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AppServices {

    @GET("products")
    suspend fun getProductByPage(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): Response<ProductModel>


    @GET("products/categories")
    suspend fun getCategories(
    ): Response<List<CategoriesModelItem>>

    @GET("products/{id}")
    suspend fun getProductItem(
        @Path("id") id: Long
    ): Response<Product>
}