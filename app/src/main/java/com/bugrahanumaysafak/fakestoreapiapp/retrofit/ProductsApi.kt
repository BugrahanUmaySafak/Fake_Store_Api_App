package com.bugrahanumaysafak.fakestoreapiapp.retrofit

import com.bugrahanumaysafak.fakestoreapiapp.entity.ProductsResponseItem
import com.bugrahanumaysafak.fakestoreapiapp.entity.Rating
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductsApi {
    @GET("products")
    suspend fun getProducts(): Response<List<ProductsResponseItem>>

    @DELETE("products/{id}")
    suspend fun deleteProducts(@Path("id") id: Int): Response<Unit>

    @POST("products")
    suspend fun addProducts(
        @Body productsResponseItem: ProductsResponseItem
    ): Response<ProductsResponseItem>

}