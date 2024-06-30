package com.example.foodfinder.network

import com.example.foodfinder.model.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("api/v1/product/{id}")
    suspend fun getProduct(
        @Path("id") id: Int,
    ): Response<Product>
}
