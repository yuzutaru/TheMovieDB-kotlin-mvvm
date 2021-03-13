package com.yuzu.themoviedb.model.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.yuzu.themoviedb.model.State
import com.yuzu.themoviedb.model.data.ReviewData
import com.yuzu.themoviedb.model.repository.MovieRepository
import com.yuzu.themoviedb.utils.API_KEY
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

/**
 * Created by Yustar Pramudana on 13/03/2021
 */

class ReviewDataSource(private val movieRepository: MovieRepository, private val compositeDisposable: CompositeDisposable, private val id: Int): PageKeyedDataSource<Int, ReviewData>() {
    var state: MutableLiveData<State> = MutableLiveData()
    private var retryCompletable: Completable? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, ReviewData>) {
        updateState(State.LOADING)
        compositeDisposable.add(
            movieRepository.reviews(id, 1, API_KEY)
                .subscribe(
                    { response ->
                        if (!response.reviewData.isNullOrEmpty()) {
                            updateState(State.DONE)
                            callback.onResult(response.reviewData!!,null,2)

                        } else {
                            updateState(State.ERROR)
                        }
                    },
                    {
                        updateState(State.ERROR)
                    }
                )
        )
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ReviewData>) {
        updateState(State.LOADING)
        compositeDisposable.add(
            movieRepository.reviews(id, params.key, API_KEY)
                .subscribe(
                    { response ->
                        if (!response.reviewData.isNullOrEmpty()) {
                            updateState(State.DONE)
                            callback.onResult(response.reviewData!!, params.key + 1)

                        } else {
                            updateState(State.ERROR)
                        }
                    },
                    {
                        updateState(State.ERROR)
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ReviewData>) {

    }

    private fun updateState(state: State) {
        this.state.postValue(state)
    }

    fun retry() {
        if (retryCompletable != null) {
            compositeDisposable.add(retryCompletable!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe())
        }
    }

    private fun setRetry(action: Action?) {
        retryCompletable = if (action == null) null else Completable.fromAction(action)
    }
}