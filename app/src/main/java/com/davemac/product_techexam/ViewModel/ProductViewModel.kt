package com.davemac.product_techexam.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.davemac.product_techexam.Data.Local.Interface.ProductDao
import com.davemac.product_techexam.Model.Categories.CategoriesModel
import com.davemac.product_techexam.Model.Products.Product
import com.davemac.product_techexam.Model.Products.ProductModel
import com.davemac.product_techexam.Repository.ProductRepository
import com.davemac.product_techexam.Utils.Constant.SERVER_ERROR
import com.davemac.product_techexam.Utils.Constant.TAG
import com.davemac.product_techexam.Utils.DataStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductViewModel(private val productRepository: ProductRepository, private val productDao:  ProductDao) : ViewModel() {

    private val _productList = MutableLiveData<DataStatus<ProductModel>>()

    val productList: LiveData<DataStatus<ProductModel>> get() = _productList

    private val _categoryList = MutableLiveData<DataStatus<List<CategoriesModel>>>()

    val categoryList: LiveData<DataStatus<List<CategoriesModel>>> get() = _categoryList

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val productItem: MutableLiveData<DataStatus<Product>> = MutableLiveData()

    val productErrorMsg: MutableLiveData<String> = MutableLiveData()

    private val _cacheProducts: Flow<List<Product>> = productDao.getAllProducts()

    val cacheProducts: LiveData<List<Product>> = _cacheProducts.asLiveData()

    fun loadProducts(skip: Int) = viewModelScope.launch{

        productRepository.getProductByPage(skip).collect{
             _productList.value = it
        }

    }

    fun loadCategories() = viewModelScope.launch{

        productRepository.getCategory().collect{
             _categoryList.value = it
         }
    }

    fun getProductItem(id: Long) {
        coroutineScope.launch {

            val result = productRepository.getProductItem(id)

            withContext(Dispatchers.Main){
                when (result.code()) {
                    200 -> {
                            productItem.postValue(DataStatus.success(result.body()))
                    }
                    400 -> {
                            productErrorMsg.postValue(result.message())
                    }else -> {
                            productErrorMsg.postValue(SERVER_ERROR)
                    }
                }
            }
        }


        }

}