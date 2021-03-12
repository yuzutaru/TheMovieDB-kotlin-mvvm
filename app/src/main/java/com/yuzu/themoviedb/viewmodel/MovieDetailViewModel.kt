package com.yuzu.themoviedb.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yuzu.themoviedb.model.data.MovieData
import com.yuzu.themoviedb.utils.ARGUMENT_MOVIE_DATA

/**
 * Created by Yustar Pramudana on 12/03/2021
 */

class MovieDetailViewModel: ViewModel() {
    private val LOG_TAG = "MovieDetail"
    var loading: MutableLiveData<Boolean> = MutableLiveData(false)

    var movieData = MutableLiveData<Int>()
    fun movieDataLive(): LiveData<Int> = movieData

    fun getData(arguments: Bundle?) {
        if (arguments != null) {
            movieData.value = arguments.get(ARGUMENT_MOVIE_DATA) as Int
        }
    }

    fun detail() {}
}