package com.yuzu.themoviedb.model.api

import com.yuzu.themoviedb.model.data.Movie
import com.yuzu.themoviedb.model.data.MovieDetail
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Yustar Pramudana on 10/03/2021
 */

interface MovieAPI {
    /**
     * Popular
     * */
    @GET(value = "movie/popular")
    fun popular(@Query("page") page: Int, @Query("api_key") apiKey: String): Single<Movie>

    /**
     * Top Rated
     * */
    @GET(value = "movie/top_rated")
    fun topRated(@Query("page") page: Int, @Query("api_key") apiKey: String): Single<Movie>

    /**
     * Now Playing
     * */
    @GET(value = "movie/now_playing")
    fun nowPlaying(@Query("page") page: Int, @Query("api_key") apiKey: String): Single<Movie>

    /**
     * Movie Details
     * */
    @GET(value = "movie/{id}")
    fun details(@Path("id") id: Int, @Query("api_key") apiKey: String): Single<MovieDetail>
}