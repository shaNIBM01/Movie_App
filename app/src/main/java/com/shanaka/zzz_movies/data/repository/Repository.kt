package com.shanaka.zzz_movies.data.repository

import com.shanaka.zzz_movies.data.api.RetrofitInstance
import com.shanaka.zzz_movies.data.model.MovieResponse
import retrofit2.Response

class Repository {
    private val key = "389556a5ea0ca785735dd13e7610c87b"

    suspend fun getLatestMovies(page: Int = 1): Response<MovieResponse> {
        return RetrofitInstance.api.getLatestMovies(apiKey = key, page = page)
    }

    suspend fun searchMovies(query: String, page: Int = 1): Response<MovieResponse> {
        return RetrofitInstance.api.searchMovies(apiKey = key, query = query, page = page)
    }

}
