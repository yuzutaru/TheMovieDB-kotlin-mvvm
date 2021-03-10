package com.yuzu.themoviedb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Yustar Pramudana on 10/03/2021
 */

class MainMenuViewModel: ViewModel() {
    private val LOG_TAG = "User"
    var loading: MutableLiveData<Boolean> = MutableLiveData(false)
}