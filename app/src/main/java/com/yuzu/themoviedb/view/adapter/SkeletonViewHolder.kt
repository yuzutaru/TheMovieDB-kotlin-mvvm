package com.yuzu.themoviedb.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yuzu.themoviedb.R
import com.yuzu.themoviedb.databinding.SkeletonMovieBinding
import com.yuzu.themoviedb.model.State

/**
 * Created by Yustar Pramudana on 12/03/2021
 */

class SkeletonViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = SkeletonMovieBinding.bind(view)

    fun create(parent: ViewGroup): SkeletonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.skeleton_movie, parent, false)
        return SkeletonViewHolder(view)
    }

    fun bind(status: State?) {
        binding.skeletonLayout.visibility = if (status == State.LOADING) View.VISIBLE else View.INVISIBLE
        binding.txtError.visibility = if (status == State.ERROR) View.VISIBLE else View.INVISIBLE

        binding.skeletonConstraint.isEnabled = status == State.LOADING
    }
}