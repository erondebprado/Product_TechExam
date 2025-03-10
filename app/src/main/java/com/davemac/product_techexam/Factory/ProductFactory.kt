package com.davemac.product_techexam.Factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.davemac.product_techexam.Data.Local.ProductRoomDatabase
import com.davemac.product_techexam.Repository.ProductRepository
import com.davemac.product_techexam.ViewModel.ProductViewModel

class ProductFactory(private val productRepository: ProductRepository, val context: Context): ViewModelProvider.Factory {

    val database by lazy { ProductRoomDatabase.invoke(context).productDao() }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ProductViewModel::class.java!!)) {
            ProductViewModel(productRepository, database) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}