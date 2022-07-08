package com.jittyandiyan.androidApp.demo.features.tvshows.shows.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.jittyandiyan.androidApp.databinding.TvshowAdapterItemBinding
import com.kmmt.models.demotvshowsearch.domain.TVShowInfo

class TVShowsAdapter : ListAdapter<TVShowInfo, TVShowViewHolder>(postCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVShowViewHolder {
        var binding = TvshowAdapterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TVShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TVShowViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    companion object {
        private val postCallback = object : DiffUtil.ItemCallback<TVShowInfo>() {
            override fun areContentsTheSame(oldItem: TVShowInfo, newItem: TVShowInfo): Boolean = oldItem == newItem
            override fun areItemsTheSame(oldItem: TVShowInfo, newItem: TVShowInfo): Boolean = oldItem.id == newItem.id
        }
    }
}