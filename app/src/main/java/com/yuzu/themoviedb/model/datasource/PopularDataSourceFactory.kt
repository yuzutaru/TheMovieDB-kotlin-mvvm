package com.yuzu.themoviedb.model.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.yuzu.themoviedb.model.data.MovieData
import com.yuzu.themoviedb.model.repository.MovieRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Yustar Pramudana on 12/03/2021
 */

class PopularDataSourceFactory(private val movieRepository: MovieRepository, private val compositeDisposable: CompositeDisposable): DataSource.Factory<Int, MovieData>() {
    val popularDataSourceLiveData = MutableLiveData<PopularDataSource>()

    override fun create(): DataSource<Int, MovieData> {
        val popularDataSource = PopularDataSource(movieRepository, compositeDisposable)
        popularDataSourceLiveData.postValue(popularDataSource)
        return popularDataSource
    }
}