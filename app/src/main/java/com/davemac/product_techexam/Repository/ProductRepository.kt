package com.davemac.product_techexam.Repository

import android.util.Log
import com.davemac.product_techexam.Data.Remote.RetrofitInstance
import com.davemac.product_techexam.Utils.Constant.LIMIT
import com.davemac.product_techexam.Utils.Constant.TAG
import com.davemac.product_techexam.Utils.DataStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn



class ProductRepository {

    private val client = RetrofitInstance().getInstance()

    suspend fun getProductByPage(skip: Int) = flow {

        emit(DataStatus.loading())
        val result = client.getProductByPage(LIMIT, skip)

        Log.d(TAG, "Product skip " + skip)
        Log.d(TAG, "Product repository " + result.body())

        when(result.code()){
            200 -> { emit(DataStatus.success(result.body()))  }
            400 -> {emit(DataStatus.error(result.message()))}
            500 -> {emit(DataStatus.error(result.message()))}
        }
    }.catch {
        emit(DataStatus.error(it.message.toString()))
    }.flowOn(Dispatchers.IO)

    suspend fun getProductItem2(id: Long) = flow {
        emit(DataStatus.loading())
        val result = client.getProductItem(id)

        Log.d(TAG, "Product item repository " + result.body())

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