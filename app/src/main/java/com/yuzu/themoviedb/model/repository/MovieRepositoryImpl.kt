package com.yuzu.themoviedb.model.repository

import com.yuzu.themoviedb.model.api.MovieAPI
import com.yuzu.themoviedb.model.data.Movie
import com.yuzu.themoviedb.model.data.MovieDetail
import com.yuzu.themoviedb.model.data.Review
import io.reactivex.Single

/**
 * Created by Yustar Pramudana on 10/03/2021
 */

class MovieRepositoryImpl(private val api: MovieAPI): MovieRepository {
    override fun popular(page: Int, apiKey: String): Single<Movie> {
        return api.popular(page, apiKey)
    }

    override fun topRated(page: Int, apiKey: String): Single<Movie> {
        return api.topRated(page, apiKey)
    }

    override fun nowPlaying(page: Int, apiKey: String): Single<Movie> {
        return api.nowPlaying(page, apiKey)
    }

    override fun details(id: Int, apiKey: String): Single<MovieDetail> {
        return api.details(id, apiKey)
    }

    override fun reviews(id: Int, page: Int, apiKey: String): Single<Review> {
        return api.reviews(id, page, apiKey)
    }
}