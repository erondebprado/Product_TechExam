package com.davemac.product_techexam.Repository

import android.content.Context
import android.util.Log
import com.davemac.product_techexam.Data.Local.ProductRoomDatabase
import com.davemac.product_techexam.Data.Remote.RetrofitInstance
import com.davemac.product_techexam.Model.Products.Product
import com.davemac.product_techexam.Utils.Constant.LIMIT
import com.davemac.product_techexam.Utils.Constant.TAG
import com.davemac.product_techexam.Utils.DataStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn



class ProductRepository(private val context: Context) {

    val database by lazy { ProductRoomDatabase.invoke(context).productDao() }

    private val client = RetrofitInstance().getInstance()

    suspend fun getProductByPage(skip: Int) = flow {

        emit(DataStatus.loading())
        val result = client.getProductByPage(LIMIT, skip)

        when(result.code()){
            200 -> {

                result.body()!!.products.forEach {
                     database.insertProducts(Product(it.id, it.description, it.price, it.rating, it.thumbnail, it.title))
                }
                emit(DataStatus.success(result.body()))
            }
            400 -> {emit(DataStatus.error(result.message()))}
            500 -> {emit(DataStatus.error(result.message()))}
        }
    }.catch {
        emit(DataStatus.error(it.message.toString()))
    }.flowOn(Dispatchers.IO)


    suspend fun getCategory() = flow {

        emit(DataStatus.loading())
        val result = client.getCategories()

        when(result.code()){
            200 -> { emit(DataStatus.success(result.body()))  }
            400 -> {emit(DataStatus.error(result.message()))}
            500 -> {emit(DataStatus.error(result.message()))}
        }
    }.catch {
        emit(DataStatus.error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    suspend fun getProductItem(id: Long) = client.getProductItem(id)

}