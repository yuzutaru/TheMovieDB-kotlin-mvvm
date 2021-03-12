package com.yuzu.themoviedb.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.SimpleItemAnimator
import com.yuzu.themoviedb.databinding.FragmentMainMenuBinding
import com.yuzu.themoviedb.utils.ARGUMENT_NOW_PLAYING
import com.yuzu.themoviedb.utils.ARGUMENT_POPULAR
import com.yuzu.themoviedb.utils.ARGUMENT_TOP_RATED
import com.yuzu.themoviedb.utils.BOTTOM_SHEET_DIALOG
import com.yuzu.themoviedb.view.adapter.MovieAdapter
import com.yuzu.themoviedb.viewmodel.MainMenuViewModel
import java.util.*

/**
 * Created by Yustar Pramudana on 10/03/2021
 */

class MainMenuFragment: Fragment() {
    private val LOG_TAG = "User"
    private lateinit var binding: FragmentMainMenuBinding
    private lateinit var viewModel: MainMenuViewModel

    private lateinit var movieAdapter: MovieAdapter

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
        initAdapter()
        initState()
    }

    private fun initAdapter() {
        movieAdapter = MovieAdapter(viewModel) { viewModel.retry() }
        binding.recyclerView.adapter = movieAdapter
        viewModel.type.value = ARGUMENT_POPULAR
        viewModel.moviePagedList.observe(viewLifecycleOwner, {
            try {
                Log.e("Paging ", "PageAll" + it.size)
                try {
                    //to prevent animation recyclerview when change the list
                    binding.recyclerView.itemAnimator = null
                    (Objects.requireNonNull(binding.recyclerView.getItemAnimator()) as SimpleItemAnimator).supportsChangeAnimations = false
                } catch (e: Exception) {
                }
                movieAdapter.submitList(it)
            } catch (e: Exception) {
            }
        })
    }

    private fun initState() {
        viewModel.getState().observe(viewLifecycleOwner, { state ->
            viewModel.recyclerViewVisibility(binding, state, movieAdapter)
            //footerBinding.progressBar.visibility = if (viewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            //footerBinding.txtError.visibility = if (viewModel.listIsEmpty() && state == State.ERROR) View.VISIBLE else View.GONE
        })
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
        viewModel.type.value = ARGUMENT_POPULAR
    }

    fun topRatedOnClick() {
        Log.d(LOG_TAG, "popUpOnItemClick:TOP_RATED OnClick")
        viewModel.type.value = ARGUMENT_TOP_RATED
    }

    fun nowPlayingOnClick() {
        Log.d(LOG_TAG, "popUpOnItemClick:NOW_PLAYING OnClick")
        viewModel.type.value = ARGUMENT_NOW_PLAYING
    }
}