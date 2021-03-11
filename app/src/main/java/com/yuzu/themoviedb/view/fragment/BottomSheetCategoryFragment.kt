package com.yuzu.themoviedb.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yuzu.themoviedb.databinding.DialogBottomSheetCategoryBinding
import com.yuzu.themoviedb.viewmodel.BottomSheetCategoryViewModel

/**
 * Created by Yustar Pramudana on 12/03/2021
 */

class BottomSheetCategoryFragment: BottomSheetDialogFragment() {
    private val LOG_TAG = "BottomSHeet"
    private lateinit var binding: DialogBottomSheetCategoryBinding
    private lateinit var viewModel: BottomSheetCategoryViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this).get(BottomSheetCategoryViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogBottomSheetCategoryBinding.inflate(inflater, container, false)

        viewModel.setTransparent(dialog)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.popular.setOnClickListener {
            (parentFragment as MainMenuFragment).popularOnClick()
            dismiss()
        }

        binding.topRated.setOnClickListener {
            (parentFragment as MainMenuFragment).topRatedOnClick()
            dismiss()
        }

        binding.nowPlaying.setOnClickListener {
            (parentFragment as MainMenuFragment).nowPlayingOnClick()
            dismiss()
        }
    }
}