package com.juarezcode.artbooktesting.api

import com.juarezcode.artbooktesting.model.ImageResponse
import com.juarezcode.artbooktesting.util.Util
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {

    @GET("api/")
    suspend fun imageSearch(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = Util.API_KEY,
    ): Response<ImageResponse>
}