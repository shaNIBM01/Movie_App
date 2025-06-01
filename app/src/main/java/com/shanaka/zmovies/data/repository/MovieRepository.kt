package com.shanaka.zmovies.data.repository

import com.shanaka.zmovies.data.api.RetrofitInstance
import com.shanaka.zmovies.data.model.Genre

class MovieRepository {
    suspend fun getLatestMovies(page: Int = 1) =
        RetrofitInstance.api.getLatestMovies(page)

    suspend fun searchMovies(query: String, page: Int = 1) =
        RetrofitInstance.api.searchMovies(query, page)

    suspend fun getGenres(): List<Genre> {
        return try {
            RetrofitInstance.api.getGenres().genres
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getMovieDetails(movieId: Int) =
        RetrofitInstance.api.getMovieDetails(movieId.toString())
}
