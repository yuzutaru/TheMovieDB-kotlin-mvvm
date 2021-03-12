package com.yuzu.themoviedb.model.repository

import com.yuzu.themoviedb.model.api.MovieAPI
import com.yuzu.themoviedb.model.data.Movie
import io.reactivex.Single

/**
 * Created by Yustar Pramudana on 10/03/2021
 */

class MovieRepositoryImpl(private val api: MovieAPI): MovieRepository {
    override fun popular(page: Int, apiKey: String): Single<Movie> {
        return api.popular(page, apiKey)
    }

    override fun topRated(apiKey: String): Single<Movie> {
        return api.topRated(apiKey)
    }

    override fun nowPlaying(apiKey: String): Single<Movie> {
        return api.nowPlaying(apiKey)
    }
}