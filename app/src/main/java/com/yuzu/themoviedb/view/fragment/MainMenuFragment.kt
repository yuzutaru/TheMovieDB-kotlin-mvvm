package com.yuzu.themoviedb.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yuzu.themoviedb.databinding.FragmentMainMenuBinding
import com.yuzu.themoviedb.utils.BOTTOM_SHEET_DIALOG
import com.yuzu.themoviedb.viewmodel.MainMenuViewModel

/**
 * Created by Yustar Pramudana on 10/03/2021
 */

class MainMenuFragment: Fragment() {
    private val LOG_TAG = "User"
    private lateinit var binding: FragmentMainMenuBinding
    private lateinit var viewModel: MainMenuViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this).get(MainMenuViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainMenuBinding.inflate(inflater, container, false).apply {
            viewModel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        categoryOnClick()
    }

    private fun categoryOnClick() {
        binding.category.setOnClickListener {
            val fragmentTransaction = childFragmentManager.beginTransaction()
            val prev = childFragmentManager.findFragmentByTag(BOTTOM_SHEET_DIALOG)
            viewModel.checkPrevFragment(prev, fragmentTransaction)

            BottomSheetCategoryFragment().apply {
                show(fragmentTransaction, BOTTOM_SHEET_DIALOG)
            }
        }
    }

    fun popularOnClick() {
        Log.d(LOG_TAG, "popUpOnItemClick:POPULAR OnClick")
    }

    fun topRatedOnClick() {
        Log.d(LOG_TAG, "popUpOnItemClick:TOP_RATED OnClick")
    }

    fun nowPlayingOnClick() {
        Log.d(LOG_TAG, "popUpOnItemClick:NOW_PLAYING OnClick")
    }
}