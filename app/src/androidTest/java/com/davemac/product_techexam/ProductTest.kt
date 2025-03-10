package com.davemac.product_techexam

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.davemac.product_techexam.Data.Local.Interface.ProductDao
import com.davemac.product_techexam.Data.Local.ProductRoomDatabase
import com.davemac.product_techexam.Model.Products.Product
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ProductTest {

    lateinit var productRoomDatabase: ProductRoomDatabase
    lateinit var productDao: ProductDao

    @Before
    fun setup(){
        productRoomDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ProductRoomDatabase::class.java).allowMainThreadQueries().build()

        productDao = productRoomDatabase.productDao()
    }

    @Test
    fun insertProducts() = runBlocking{

         val products = Product(1,
            "The Essence Mascara Lash Princess is a popular mascara known ",
            9.99, 4.94,
            "https://cdn.dummyjson.com/products/images/beauty/Essence%20Mascara%20Lash%20Princess/thumbnail.png",
            "Essence Mascara Lash Princess")
        productDao.insertProducts(products)

        val getResult = productDao.getAllProducts()

        assertEquals(1, getResult.first().get(0).id)
        assertEquals(2, getResult.first().get(0).id)

    }

    @Test
    fun countProduct() = runBlocking{

        val products = Product(1,
            "The Essence Mascara Lash Princess is a popular mascara known",
            9.99, 4.94,
            "https://cdn.dummyjson.com/products/images/beauty/Essence%20Mascara%20Lash%20Princess/thumbnail.png",
            "Essence Mascara Lash Princess")

        productDao.insertProducts(products)

        val getResult = productDao.getProducts()

        assertEquals(1, getResult.size)
        assertEquals(2, getResult.size)
    }
}