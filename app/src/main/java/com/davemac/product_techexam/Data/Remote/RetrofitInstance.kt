package com.davemac.product_techexam.Data.Remote

import com.davemac.product_techexam.Utils.Constant.API_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    fun getInstance(): AppServices {
        return Retrofit.Builder().baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(AppServices::class.java)
    }
}