package com.yuzu.themoviedb.viewmodel

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yuzu.themoviedb.R
import com.yuzu.themoviedb.TheMovieDBApplication
import com.yuzu.themoviedb.model.NoNetworkException
import com.yuzu.themoviedb.model.Response
import com.yuzu.themoviedb.model.Status
import com.yuzu.themoviedb.model.data.MovieDetail
import com.yuzu.themoviedb.model.repository.MovieRepository
import com.yuzu.themoviedb.utils.API_KEY
import com.yuzu.themoviedb.utils.ARGUMENT_MOVIE_DATA
import com.yuzu.themoviedb.view.fragment.MovieDetailFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Yustar Pramudana on 12/03/2021
 */

class MovieDetailViewModel: ViewModel() {
    private val LOG_TAG = "MovieDetail"
    var loading: MutableLiveData<Boolean> = MutableLiveData(true)

    private val compositeDisposable = CompositeDisposable()
    private val movieRepository: MovieRepository

    private val movieId = MutableLiveData<Int>()
    fun movieIdDataLive(): LiveData<Int> = movieId

    private var movieDetail = MutableLiveData<Response<MovieDetail>>()
    fun movieDetailDataLive(): LiveData<Response<MovieDetail>> = movieDetail

    var movieDetailData = MutableLiveData<MovieDetail>()
    fun movieDetailResDataLive(): LiveData<MovieDetail> = movieDetailData

    var genre = MutableLiveData<String>()

    init {
        val appComponent = TheMovieDBApplication.instance.getAppComponent()
        movieRepository = appComponent.movieRepository()
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun getData(arguments: Bundle?) {
        loading.value = true
        if (arguments != null) {
            movieId.value = arguments.get(ARGUMENT_MOVIE_DATA) as Int
        }
    }

    fun detail() {
        compositeDisposable.add(
            movieRepository.details(movieId.value!!, API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                            res -> movieDetail.value = Response.succeed(res)
                    },
                    {
                        movieDetail.value = when (it) {
                            is NoNetworkException -> {
                                Response.networkLost()
                            }
                            else -> Response.error(it)
                        }
                    }
                )
        )
    }

    fun detailRes(fragment: MovieDetailFragment, response: Response<MovieDetail>) {
        try {
            Log.d(LOG_TAG, "DATA STATUS = ${response.status}")

            if (response.status == Status.SUCCEED) {
                if (response.data != null) {
                    movieDetailData.value = response.data
                    loading.value = false
                }

            } else if (response.status == Status.FAILED) {
                if (response.error != null) {
                    if (response.error.message != null) {
                        if (response.error.message!!.contains("Unable to resolve host", true)) {
                            Log.e(LOG_TAG, "errorMessage : ${fragment.resources.getString(R.string.no_connection)}")
                            Toast.makeText(fragment.context, fragment.resources.getString(R.string.no_connection), Toast.LENGTH_LONG).show()

                            loading.value = false

                        } else {
                            Log.e(LOG_TAG, "errorMessage : ${response.error.message}")
                            Toast.makeText(fragment.context, response.error.message, Toast.LENGTH_LONG).show()
                            loading.value = false
                        }

                    } else {
                        Log.e(LOG_TAG, "errorMessage : General Error")
                        Toast.makeText(fragment.context, "General Error", Toast.LENGTH_LONG).show()
                        loading.value = false
                    }
                }

            } else if (response.status == Status.NO_CONNECTION) {
                Log.e(LOG_TAG,"errorMessage : ${fragment.resources.getString(R.string.no_connection)}")
                Toast.makeText(fragment.context,fragment.resources.getString(R.string.no_connection),Toast.LENGTH_LONG).show()
                loading.value = false
            }
        } catch (e: Exception) {
            e.message?.let { Log.e(LOG_TAG, it) }
            loading.value = false
        }
    }

    fun genre() {
        var result = ""
        if (movieDetailData.value!!.genres != null) {
            for (i in movieDetailData.value!!.genres!!.indices) {
                if (movieDetailData.value!!.genres!![i].name != null)
                    result += if (i == movieDetailData.value!!.genres!!.size - 1)
                        movieDetailData.value!!.genres!![i].name!!
                    else
                        "${movieDetailData.value!!.genres!![i].name}, "
            }
        }

        genre.value = result
    }
}