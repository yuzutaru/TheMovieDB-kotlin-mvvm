package com.yuzu.themoviedb.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yuzu.themoviedb.R
import com.yuzu.themoviedb.databinding.FragmentMovieDetailBinding
import com.yuzu.themoviedb.model.data.MovieData
import com.yuzu.themoviedb.model.data.MovieDetail
import com.yuzu.themoviedb.utils.IMG_URL
import com.yuzu.themoviedb.view.activity.MainActivity
import com.yuzu.themoviedb.viewmodel.MovieDetailViewModel

/**
 * Created by Yustar Pramudana on 12/03/2021
 */

class MovieDetailFragment: Fragment() {
    private val LOG_TAG = "MovieDetail"
    private lateinit var binding: FragmentMovieDetailBinding
    private lateinit var viewModel: MovieDetailViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false).apply {
            viewmodel = viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        backOnClick()
        onBackPressed()

        viewModel.getData(arguments)
        viewModel.movieIdDataLive().observe(viewLifecycleOwner, { viewModel.detail() })
        viewModel.movieDetailDataLive().observe(viewLifecycleOwner, { viewModel.detailRes(this, it) })
        viewModel.movieDetailResDataLive().observe(viewLifecycleOwner, { setImage(it) })
    }

    private fun setImage(data: MovieDetail) {
        Glide.with(this).load(IMG_URL + data.backdropPath).diskCacheStrategy(DiskCacheStrategy.DATA).into(binding.backdrop)
        Glide.with(this).load(IMG_URL + data.posterPath).diskCacheStrategy(DiskCacheStrategy.DATA).into(binding.photo)
        viewModel.genre()
    }

    private fun backOnClick() {
        binding.back.setOnClickListener {
            (activity as MainActivity).replaceFragment(R.id.main_content, MovieFragment(), null)
        }
    }

    private fun onBackPressed() {
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (activity as MainActivity).replaceFragment(R.id.main_content, MovieFragment(), null)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }
}