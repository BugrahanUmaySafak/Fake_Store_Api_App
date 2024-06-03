package com.bugrahanumaysafak.fakestoreapiapp.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val baseUrl = "https://fakestoreapi.com/"

    private fun getInstance(): Retrofit {

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val productsApi : ProductsApi = getInstance().create(ProductsApi::class.java)

}