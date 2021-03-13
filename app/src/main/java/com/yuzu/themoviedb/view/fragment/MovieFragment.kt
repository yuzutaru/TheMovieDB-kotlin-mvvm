package com.yuzu.themoviedb.view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.SimpleItemAnimator
import com.yuzu.themoviedb.R
import com.yuzu.themoviedb.databinding.FragmentMovieBinding
import com.yuzu.themoviedb.model.data.MovieData
import com.yuzu.themoviedb.utils.*
import com.yuzu.themoviedb.view.activity.MainActivity
import com.yuzu.themoviedb.view.adapter.MovieAdapter
import com.yuzu.themoviedb.viewmodel.MovieViewModel
import java.util.*

/**
 * Created by Yustar Pramudana on 10/03/2021
 */

class MovieFragment: Fragment() {
    private val LOG_TAG = "Movie"
    private lateinit var binding: FragmentMovieBinding
    private lateinit var viewModel: MovieViewModel

    private lateinit var movieAdapter: MovieAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMovieBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        onBackPressed()
        categoryOnClick()
        initAdapter()
        initState()
        likeOnClick()

        viewModel.movieDataLive().observe(viewLifecycleOwner, { movieDetail(it) })
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

    fun favoriteOnClick() {
        Log.d(LOG_TAG, "popUpOnItemClick:FAVORITE OnClick")
        viewModel.type.value = ARGUMENT_FAVORITE
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

    private fun likeOnClick() {
        binding.like.setOnClickListener {
            viewModel.likeOnClick(binding.like, binding.unlike)
            viewModel.type.value = ARGUMENT_POPULAR
        }

        binding.unlike.setOnClickListener {
            viewModel.likeOnClick(binding.like, binding.unlike)
            viewModel.type.value = ARGUMENT_FAVORITE
        }
    }

    private fun movieDetail(data: Int) {
        val bundle = bundleOf(ARGUMENT_MOVIE_DATA to data)
        (activity as MainActivity).replaceFragment(R.id.main_content, MovieDetailFragment(), bundle)
    }

    private fun onBackPressed() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                //(activity as MainActivity).replaceFragment(R.id.main_content, MovieFragment(), null)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}