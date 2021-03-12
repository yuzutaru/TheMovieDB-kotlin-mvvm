package com.yuzu.themoviedb.viewmodel

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.yuzu.themoviedb.TheMovieDBApplication
import com.yuzu.themoviedb.databinding.FragmentMainMenuBinding
import com.yuzu.themoviedb.model.State
import com.yuzu.themoviedb.model.data.MovieData
import com.yuzu.themoviedb.model.datasource.PopularDataSource
import com.yuzu.themoviedb.model.datasource.PopularDataSourceFactory
import com.yuzu.themoviedb.model.repository.MovieRepository
import com.yuzu.themoviedb.view.adapter.MovieAdapter
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by Yustar Pramudana on 10/03/2021
 */

class MainMenuViewModel: ViewModel() {
    private val LOG_TAG = "MainMenu"
    var loading: MutableLiveData<Boolean> = MutableLiveData(false)

    private val compositeDisposable = CompositeDisposable()
    private val movieRepository: MovieRepository
    private val popularDataSourceFactory: PopularDataSourceFactory

    var moviePagedList: LiveData<PagedList<MovieData>>

    private val pageSize = 5

    init {
        val appComponent = TheMovieDBApplication.instance.getAppComponent()
        movieRepository = appComponent.movieRepository()

        popularDataSourceFactory = PopularDataSourceFactory(movieRepository, compositeDisposable)
        val config = PagedList.Config.Builder().setPageSize(pageSize).setInitialLoadSizeHint(pageSize).setEnablePlaceholders(false).build()
        moviePagedList = LivePagedListBuilder(popularDataSourceFactory, config).build()
    }

    fun checkPrevFragment(prev: Fragment?, fragmentTransaction: FragmentTransaction) {
        if (prev != null) {
            fragmentTransaction.remove(prev)
        }
        fragmentTransaction.addToBackStack(null)
    }

    fun retry() {
        popularDataSourceFactory.popularDataSourceLiveData.value?.retry()
    }

    fun getState(): LiveData<State> = Transformations.switchMap(
        popularDataSourceFactory.popularDataSourceLiveData,
        PopularDataSource::state
    )

    private fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }

    fun recyclerViewVisibility(binding: FragmentMainMenuBinding, state: State, movieAdapter: MovieAdapter) {
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
            movieAdapter.setState(state)
        }
    }
}