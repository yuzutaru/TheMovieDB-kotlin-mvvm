package com.yuzu.themoviedb.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yuzu.themoviedb.R
import com.yuzu.themoviedb.databinding.ItemMovieBinding
import com.yuzu.themoviedb.model.data.MovieData
import com.yuzu.themoviedb.model.data.ReviewData
import com.yuzu.themoviedb.utils.ARGUMENT_MOVIE
import com.yuzu.themoviedb.utils.IMG_URL
import com.yuzu.themoviedb.viewmodel.MovieDetailViewModel
import com.yuzu.themoviedb.viewmodel.MovieViewModel

/**
 * Created by Yustar Pramudana on 12/03/2021
 */

class MovieViewHolder(private val viewModel: ViewModel, view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemMovieBinding.bind(view)

    fun create(parent: ViewGroup): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(viewModel, view)
    }

    @SuppressLint("SetTextI18n")
    fun bind(data: MovieData?) {
        if (data != null) {
            Glide.with(itemView).load(IMG_URL + data.posterPath).diskCacheStrategy(DiskCacheStrategy.DATA).into(binding.photo)

            binding.title.text = data.title
            binding.rating.text = "(${data.voteAverage})"
            binding.releaseDate.text = data.releaseDate
            binding.overview.text = data.overview

            binding.background.setOnClickListener {
                (viewModel as MovieViewModel).itemOnClick(data.id)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun bind(data: ReviewData?) {
        if (data != null) {
            if (data.authorDetails != null) {
                if (data.authorDetails!!.avatarPath != null) {
                    if (data.authorDetails!!.avatarPath!!.contains("https", true))
                        Glide.with(itemView).load(data.authorDetails!!.avatarPath!!.substring(1, data.authorDetails!!.avatarPath!!.length))
                            .diskCacheStrategy(DiskCacheStrategy.DATA).into(binding.photo)
                    else
                        Glide.with(itemView).load(IMG_URL + data.authorDetails!!.avatarPath).diskCacheStrategy(DiskCacheStrategy.DATA).into(binding.photo)
                }

                if (data.authorDetails!!.rating != null)
                    binding.rating.text = "(${data.authorDetails!!.rating})"
                else
                    binding.rating.text = ""
            }

            binding.title.text = data.author
            binding.releaseDate.text = data.createdAt
            binding.overview.maxLines = Int.MAX_VALUE
            binding.overview.text = data.content
        }
    }
}