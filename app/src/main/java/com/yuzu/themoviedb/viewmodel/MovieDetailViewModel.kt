package com.yuzu.themoviedb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Yustar Pramudana on 12/03/2021
 */

class MovieDetailViewModel: ViewModel() {
    private val LOG_TAG = "MovieDetail"
    var loading: MutableLiveData<Boolean> = MutableLiveData(false)
}