package com.yuzu.themoviedb.model.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.yuzu.themoviedb.model.State
import com.yuzu.themoviedb.model.data.MovieData
import com.yuzu.themoviedb.model.repository.MovieDBRepository
import com.yuzu.themoviedb.model.repository.MovieRepository
import com.yuzu.themoviedb.utils.*
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

/**
 * Created by Yustar Pramudana on 12/03/2021
 */

class MovieDataSource(private val movieRepository: MovieRepository, private val movieDBRepository: MovieDBRepository, private val compositeDisposable: CompositeDisposable,
                      private val type: String): PageKeyedDataSource<Int, MovieData>() {
    var state: MutableLiveData<State> = MutableLiveData()
    private var retryCompletable: Completable? = null

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, MovieData>) {
        updateState(State.LOADING)
        when (type) {
            ARGUMENT_POPULAR -> {
                compositeDisposable.add(
                    movieRepository.popular(1, API_KEY)
                        .subscribe(
                            { response ->
                                if (!response.movieData.isNullOrEmpty()) {
                                    updateState(State.DONE)
                                    callback.onResult(response.movieData!!,null,2)

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
            ARGUMENT_TOP_RATED -> {
                compositeDisposable.add(
                    movieRepository.topRated(1, API_KEY)
                        .subscribe(
                            { response ->
                                if (!response.movieData.isNullOrEmpty()) {
                                    updateState(State.DONE)
                                    callback.onResult(response.movieData!!,null,2)

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
            ARGUMENT_NOW_PLAYING -> {
                compositeDisposable.add(
                    movieRepository.nowPlaying(1, API_KEY)
                        .subscribe(
                            { response ->
                                if (!response.movieData.isNullOrEmpty()) {
                                    updateState(State.DONE)
                                    callback.onResult(response.movieData!!,null,2)

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
            ARGUMENT_FAVORITE -> {
                compositeDisposable.add(
                    movieDBRepository.getAll()
                        .subscribe(
                            { response ->
                                if (!response.isNullOrEmpty()) {
                                    updateState(State.DONE)
                                    callback.onResult(response,null,0)

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
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MovieData>) {
        updateState(State.LOADING)
        when (type) {
            ARGUMENT_POPULAR -> {
                compositeDisposable.add(
                    movieRepository.popular(params.key, API_KEY)
                        .subscribe(
                            { response ->
                                if (!response.movieData.isNullOrEmpty()) {
                                    updateState(State.DONE)
                                    callback.onResult(response.movieData!!, params.key + 1)

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
            ARGUMENT_TOP_RATED -> {
                compositeDisposable.add(
                    movieRepository.topRated(params.key, API_KEY)
                        .subscribe(
                            { response ->
                                if (!response.movieData.isNullOrEmpty()) {
                                    updateState(State.DONE)
                                    callback.onResult(response.movieData!!, params.key + 1)

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
            ARGUMENT_NOW_PLAYING -> {
                compositeDisposable.add(
                    movieRepository.nowPlaying(params.key, API_KEY)
                        .subscribe(
                            { response ->
                                if (!response.movieData.isNullOrEmpty()) {
                                    updateState(State.DONE)
                                    callback.onResult(response.movieData!!, params.key + 1)

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
            ARGUMENT_FAVORITE -> {
                updateState(State.DONE)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MovieData>) {

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