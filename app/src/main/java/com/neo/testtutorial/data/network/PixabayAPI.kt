package com.neo.testtutorial.data.network

import com.neo.testtutorial.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI {

    @GET("/api/")
    suspend fun searchForImage(
        @Query("q") searchQueryString: String,
        @Query("key") apiKey: String = BuildConfig.API_KEY
    )


}