package com.yuzu.themoviedb.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.yuzu.themoviedb.R
import com.yuzu.themoviedb.databinding.ItemMovieBinding
import com.yuzu.themoviedb.model.data.MovieData
import com.yuzu.themoviedb.utils.IMG_URL
import com.yuzu.themoviedb.viewmodel.MovieViewModel

/**
 * Created by Yustar Pramudana on 12/03/2021
 */

class MovieViewHolder(private val viewModel: MovieViewModel, view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemMovieBinding.bind(view)

    fun create(parent: ViewGroup): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(viewModel, view)
    }

    fun bind(data: MovieData?) {
        if (data != null) {
            Glide.with(itemView).load(IMG_URL + data.posterPath).diskCacheStrategy(DiskCacheStrategy.DATA).into(binding.photo)

            binding.title.text = data.title
            binding.releaseDate.text = data.releaseDate
            binding.overview.text = data.overview

            binding.background.setOnClickListener {
                viewModel.itemOnClick(data.id)
            }
        }
    }
}