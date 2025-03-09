package com.davemac.product_techexam.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.davemac.product_techexam.Repository.ProductRepository
import com.davemac.product_techexam.ViewModel.ProductViewModel

class ProductFactory(private val productRepository: ProductRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ProductViewModel::class.java!!)) {
            ProductViewModel(productRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}