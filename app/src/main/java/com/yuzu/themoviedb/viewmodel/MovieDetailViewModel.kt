package com.yuzu.themoviedb.viewmodel

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.yuzu.themoviedb.R
import com.yuzu.themoviedb.TheMovieDBApplication
import com.yuzu.themoviedb.databinding.FragmentMovieDetailBinding
import com.yuzu.themoviedb.model.NoNetworkException
import com.yuzu.themoviedb.model.Response
import com.yuzu.themoviedb.model.State
import com.yuzu.themoviedb.model.Status
import com.yuzu.themoviedb.model.data.MovieDetail
import com.yuzu.themoviedb.model.data.ReviewData
import com.yuzu.themoviedb.model.datasource.ReviewDataSource
import com.yuzu.themoviedb.model.datasource.ReviewDataSourceFactory
import com.yuzu.themoviedb.model.repository.MovieRepository
import com.yuzu.themoviedb.utils.API_KEY
import com.yuzu.themoviedb.utils.ARGUMENT_MOVIE_DATA
import com.yuzu.themoviedb.view.adapter.ReviewAdapter
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
    private lateinit var reviewDataSourceFactory: ReviewDataSourceFactory

    private val movieId = MutableLiveData<Int>()
    fun movieIdDataLive(): LiveData<Int> = movieId

    private var movieDetail = MutableLiveData<Response<MovieDetail>>()
    fun movieDetailDataLive(): LiveData<Response<MovieDetail>> = movieDetail

    var movieDetailData = MutableLiveData<MovieDetail>()
    fun movieDetailResDataLive(): LiveData<MovieDetail> = movieDetailData

    private val review = MutableLiveData<ReviewData>()
    fun reviewDataLive(): LiveData<ReviewData> = review

    var genre = MutableLiveData<String>()
    var id = MutableLiveData<Int>()

    lateinit var reviewPagedList: LiveData<PagedList<ReviewData>>
    private val pageSize = 4

    init {
        val appComponent = TheMovieDBApplication.instance.getAppComponent()
        movieRepository = appComponent.movieRepository()
        reviewDataSourceFactory = ReviewDataSourceFactory(movieRepository, compositeDisposable, 0)
        val config = PagedList.Config.Builder().setPageSize(pageSize).setInitialLoadSizeHint(pageSize).setEnablePlaceholders(false).build()
        //reviewPagedList = LivePagedListBuilder(reviewDataSourceFactory, config).build()

        reviewPagedList = Transformations.switchMap(id) { input ->
            return@switchMap if (input == null || input == 0) {
                //check if the current value is empty load all data else search
                LivePagedListBuilder(reviewDataSourceFactory, config).build()

            } else {
                reviewDataSourceFactory = ReviewDataSourceFactory(movieRepository, compositeDisposable, input)
                LivePagedListBuilder(reviewDataSourceFactory, config).build()
            }
        }
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun getData(arguments: Bundle?) {
        loading.value = true
        if (arguments != null) {
            movieId.value = arguments.get(ARGUMENT_MOVIE_DATA) as Int
            id.value = movieId.value
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

    fun retry() {
        reviewDataSourceFactory.reviewDataSourceLiveData.value?.retry()
    }

    fun getState(): LiveData<State> = Transformations.switchMap(
        reviewDataSourceFactory.reviewDataSourceLiveData, ReviewDataSource::state
    )

    private fun listIsEmpty(): Boolean {
        return reviewPagedList.value?.isEmpty() ?: true
    }

    fun recyclerViewVisibility(binding: FragmentMovieDetailBinding, state: State, reviewAdapter: ReviewAdapter) {
        if (listIsEmpty() && state == State.LOADING) {
            loading.value = true
            binding.recyclerView.visibility = View.GONE
            //binding.txtError.visibility = View.GONE

        } else if (listIsEmpty() && state == State.ERROR) {
            loading.value = false
            //binding.txtError.visibility = View.VISIBLE

        } else {
            loading.value = false
            binding.recyclerView.visibility = View.VISIBLE
            //binding.txtError.visibility = View.GONE
        }

        if (!listIsEmpty()) {
            reviewAdapter.setState(state)
        }
    }

    fun itemOnClick(id: String?) {
        /*if (id != null) {
            loading.value = true
            movieData.value = id
        }*/
    }

    fun likeOnClick(like: ImageView, unlike: ImageView) {
        if (unlike.visibility == View.VISIBLE) {
            unlike.visibility = View.GONE
        } else {
            unlike.visibility = View.VISIBLE
        }

        if (like.visibility == View.VISIBLE) {
            like.visibility = View.GONE
        } else {
            like.visibility = View.VISIBLE
        }
    }
}