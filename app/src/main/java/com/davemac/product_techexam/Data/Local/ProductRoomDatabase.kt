package com.davemac.product_techexam.Data.Local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.davemac.product_techexam.Data.Local.Interface.ProductDao
import com.davemac.product_techexam.Model.Products.Product

@Database(entities = arrayOf(Product::class), version = 1, exportSchema = false)
abstract class ProductRoomDatabase: RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object{
        @Volatile
        private var instance: ProductRoomDatabase? = null

        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context)  = Room.databaseBuilder(
            context.applicationContext,
            ProductRoomDatabase::class.java,
            "products_db")
            .build()

    }
}