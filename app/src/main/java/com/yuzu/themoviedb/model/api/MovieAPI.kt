package com.yuzu.themoviedb.model.api

import com.yuzu.themoviedb.model.data.Movie
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Yustar Pramudana on 10/03/2021
 */

interface MovieAPI {
    /**
     * Popular
     * */
    @GET(value = "popular")
    fun popular(@Query("api_key") apiKey: String): Single<Movie>

    /**
     * Top Rated
     * */
    @GET(value = "top_rated")
    fun topRated(@Query("api_key") apiKey: String): Single<Movie>

    /**
     * Now Playing
     * */
    @GET(value = "now_playing")
    fun nowPlaying(@Query("api_key") apiKey: String): Single<Movie>
}