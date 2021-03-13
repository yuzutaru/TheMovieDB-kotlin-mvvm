package com.yuzu.themoviedb.model.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.yuzu.themoviedb.model.data.ReviewData
import com.yuzu.themoviedb.model.repository.MovieRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Yustar Pramudana on 13/03/2021
 */

class ReviewDataSourceFactory(private val movieRepository: MovieRepository, private val compositeDisposable: CompositeDisposable, private val id: Int):
    DataSource.Factory<Int, ReviewData>() {
    val reviewDataSourceLiveData = MutableLiveData<ReviewDataSource>()

    override fun create(): DataSource<Int, ReviewData> {
        val reviewDataSource = ReviewDataSource(movieRepository, compositeDisposable, id)
        reviewDataSourceLiveData.postValue(reviewDataSource)
        return reviewDataSource
    }
}