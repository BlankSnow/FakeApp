package com.blank.fakeapp.data.remote.api

import com.blank.fakeapp.data.remote.dto.ProductDto
import com.blank.fakeapp.data.remote.dto.UserDto
import retrofit2.http.GET
import retrofit2.http.Path

interface FakeStoreApi {
    
    @GET("products")
    suspend fun getProducts(): List<ProductDto>
    
    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: Int): UserDto

    companion object {
        const val BASE_URL = "https://fakestoreapi.com/"
    }
}
