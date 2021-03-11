package com.yuzu.themoviedb.viewmodel

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Yustar Pramudana on 10/03/2021
 */

class MainMenuViewModel: ViewModel() {
    private val LOG_TAG = "MainMenu"
    var loading: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkPrevFragment(prev: Fragment?, fragmentTransaction: FragmentTransaction) {
        if (prev != null) {
            fragmentTransaction.remove(prev)
        }
        fragmentTransaction.addToBackStack(null)
    }
}