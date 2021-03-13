package com.yuzu.themoviedb.model.repository

import com.yuzu.themoviedb.model.data.Movie
import com.yuzu.themoviedb.model.data.MovieDetail
import com.yuzu.themoviedb.model.data.Review
import io.reactivex.Single
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Yustar Pramudana on 10/03/2021
 */

interface MovieRepository {
    fun popular(page: Int, apiKey: String): Single<Movie>
    fun topRated(page: Int, apiKey: String): Single<Movie>
    fun nowPlaying(page: Int, apiKey: String): Single<Movie>
    fun details(id: Int, apiKey: String): Single<MovieDetail>
    fun reviews(id: Int, page: Int, apiKey: String): Single<Review>
}