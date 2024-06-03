package com.bugrahanumaysafak.fakestoreapiapp.retrofit

import com.bugrahanumaysafak.fakestoreapiapp.entity.ProductsResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface ProductsApi {
    @GET("products")
    suspend fun getProducts(): Response<List<ProductsResponseItem>>
}