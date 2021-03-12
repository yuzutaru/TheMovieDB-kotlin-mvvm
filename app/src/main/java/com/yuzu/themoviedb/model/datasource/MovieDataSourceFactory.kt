package com.yuzu.themoviedb.model.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.yuzu.themoviedb.model.data.MovieData
import com.yuzu.themoviedb.model.repository.MovieRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Yustar Pramudana on 12/03/2021
 */

class MovieDataSourceFactory(private val movieRepository: MovieRepository, private val compositeDisposable: CompositeDisposable, private val type: String):
    DataSource.Factory<Int, MovieData>() {
    val movieDataSourceLiveData = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, MovieData> {
        val movieDataSource = MovieDataSource(movieRepository, compositeDisposable, type)
        movieDataSourceLiveData.postValue(movieDataSource)
        return movieDataSource
    }
}