package com.yuzu.themoviedb.viewmodel

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Yustar Pramudana on 12/03/2021
 */
class BottomSheetCategoryViewModel: ViewModel() {
    private val LOG_TAG = "MainMenu"
    var loading: MutableLiveData<Boolean> = MutableLiveData(false)

    fun setTransparent(dialog: Dialog?) {
        if (dialog != null && dialog.window != null) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        }
    }
}