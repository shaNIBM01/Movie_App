package com.shanaka.zmovies.data.api

import com.shanaka.zmovies.data.model.GenreResponse
import com.shanaka.zmovies.data.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getLatestMovies(
        @Query("page") page: Int = 1
    ): Response<MovieResponse>

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("page") page: Int = 1
    ): Response<MovieResponse>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: String
    ): Response<MovieResponse>

    @GET("genre/movie/list")
    suspend fun getGenres(): GenreResponse
}