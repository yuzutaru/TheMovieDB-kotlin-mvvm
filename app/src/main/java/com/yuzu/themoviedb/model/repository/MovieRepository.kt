package com.yuzu.themoviedb.model.repository

import com.yuzu.themoviedb.model.data.Movie
import com.yuzu.themoviedb.model.data.MovieDetail
import io.reactivex.Single

/**
 * Created by Yustar Pramudana on 10/03/2021
 */

interface MovieRepository {
    fun popular(page: Int, apiKey: String): Single<Movie>
    fun topRated(page: Int, apiKey: String): Single<Movie>
    fun nowPlaying(page: Int, apiKey: String): Single<Movie>
    fun details(id: Int, apiKey: String): Single<MovieDetail>
}