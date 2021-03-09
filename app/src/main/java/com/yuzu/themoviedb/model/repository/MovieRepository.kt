package com.yuzu.themoviedb.model.repository

import com.yuzu.themoviedb.model.data.Movie
import io.reactivex.Single

/**
 * Created by Yustar Pramudana on 10/03/2021
 */

interface MovieRepository {
    fun popular(apiKey: String): Single<Movie>
    fun topRated(apiKey: String): Single<Movie>
    fun nowPlaying(apiKey: String): Single<Movie>
}